package com.rancard.auth.service;

import com.rancard.auth.exception.AuthException;
import com.rancard.auth.exception.ServiceException;
import com.rancard.auth.model.UserRepository;
import com.rancard.auth.model.domain.AuthUserDetail;
import com.rancard.auth.model.dto.*;
import com.rancard.auth.model.dto.wallet.CreateWalletDto;
import com.rancard.auth.model.dto.wallet.WalletDto;
import com.rancard.auth.model.enums.UserStatus;
import com.rancard.auth.model.mongo.Role;
import com.rancard.auth.model.mongo.User;
import com.rancard.auth.model.payload.Address;
import com.rancard.auth.model.response.response.ApiResponse;
import com.rancard.auth.model.response.response.BaseError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.keycloak.representations.idm.UserRepresentation;

import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

import static com.rancard.auth.model.enums.ServiceError.USER_ALREADY_EXISTS;
import static com.rancard.auth.model.enums.ServiceError.WALLET_CREATION_EXCEPTION;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final KeycloakService keycloakService;

    private final WebClient.Builder webClientBuilder;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final RoleService roleService;
    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ServiceException(100,
                        "user not found by id: " + id ));
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() ->
                new ServiceException(100,
                        "user not found by username: " + username ));
    }

    public User getUserByKeyCloakId(String keycloakId) {
        return userRepository.findUserByKeycloakUserId(keycloakId).orElseThrow(() ->
                new ServiceException(100,
                        "user not found by keycloakId: " + keycloakId ));
    }

    public User getUserByPhone(String phone) {
        return userRepository.findByMsisdn(phone).orElseThrow(() ->
                new ServiceException(100,
                        "user not found by phone: " + phone ));
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new ServiceException(100,
                        "user not found by email: " + email ));
    }

    public void getAllRoles(){
        keycloakService.getRoles();
    }

//    public Page<User> getAllUsers(Predicate spec, Pageable pageable) {
//        return userRepository.findAll(spec, pageable);
//    }

    public List<User> getUserByIds(List<String> userIds) {
        Iterable<User> allById = userRepository.findAllById(userIds);

        List<User> foundUsers = new ArrayList<>();
        allById.forEach(foundUsers::add);

        return foundUsers;
    }

    public User signUpUser(SignupDto signUpUserDto) {

        if (!StringUtils.equals(signUpUserDto.getPassword(), signUpUserDto.getConfirmPassword()))
            throw new ServiceException(100,
                    "password do not match");

        Optional<User> optionalUser = signUpUserDto.getEmail() != null ? userRepository.findUserByEmail(signUpUserDto.getEmail()) : userRepository.findByMsisdn(signUpUserDto.getPhone());

        if (optionalUser.isPresent())
            throw new ServiceException(USER_ALREADY_EXISTS);

        List<Role> userRoles = new ArrayList<>();
        signUpUserDto.getRoles().forEach(roleDto -> userRoles.add(roleService.getRole(roleDto.getCode())));

//        UserRepresentation keycloakUser = keycloakService.registerUser(signUpUserDto);

        WalletDto walletDto = createWallet(signUpUserDto);

        if(signUpUserDto.getAddress() != null && signUpUserDto.getAddress().getGhanaPostGps() != null){
            Address address = userService.getUserAddressByGps(signUpUserDto.getAddress().getGhanaPostGps());

            if(address != null){
                signUpUserDto.setAddress(address);
            }
        }

        User user = new User();
        user.setUsername(signUpUserDto.getUsername());
        user.setEmail(signUpUserDto.getEmail());
        user.setMsisdn(signUpUserDto.getPhone());
        user.setFirstname(signUpUserDto.getFirstName());
        user.setLastname(signUpUserDto.getLastName());
        user.setOthernames(signUpUserDto.getOtherNames());
//        user.setKeycloakUserId(keycloakUser.getId());
        user.setUserStatus(UserStatus.CLEARED);
        user.setPassword(passwordEncoder.encode(signUpUserDto.getPassword()));
        user.setLastLogin(LocalDateTime.now());
        user.setLastSeen(LocalDateTime.now());
        user.setAddress(signUpUserDto.getAddress());
        user.setFamilySize(signUpUserDto.getFamilySize());
        user.setCurrentFuelSource(signUpUserDto.getCurrentFuelSource());
        user.setWalletId(walletDto.getId());
        user.setUserStatus(UserStatus.CLEARED);
        user.setRoles(new HashSet<>(userRoles));

        log.info("user: {}", user);
        return userRepository.save(user);
    }



    public User updateUser(EditUserDto editUserDto) {

        List<Role> userRoles = new ArrayList<>();
        editUserDto.getRoles().forEach(roleDto -> userRoles.add(roleService.getRole(roleDto.getCode())));

        User user = getUser(editUserDto.getId());
        user.setMsisdn(editUserDto.getPhone());
        user.setFirstname(editUserDto.getFirstName());
        user.setLastname(editUserDto.getLastName());
        user.setOthernames(editUserDto.getOtherNames());

        user.setRoles(new HashSet<>(userRoles));
        return userRepository.save(user);
    }

    public User removeUserElevatedRoles(String userId) {

        User user = getUser(userId);
        Role role = roleService.getRole(100);
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        User user = getUser(id);
        user.getRoles().removeAll(user.getRoles());
        userRepository.delete(user);
    }

    public void updateUserPassword(EditPasswordDto editPasswordDto) {
        User user = getUser(editPasswordDto.getId());

        if (user.isResetPassword())
            user.setPassword(passwordEncoder.encode(editPasswordDto.getPassword()));
        userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findUserByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (UserStatus.CLEARED != user.getUserStatus())
                throw new AuthException(1234, "This user has been blocked.");

            if (user.isResetPassword())
                throw new AuthException(1235, "This user has to reset password.");

            return new AuthUserDetail(user);
        }

        throw new UsernameNotFoundException("username: " + username + " not found");
    }

    public void updateUserStatus(String username, boolean authSuccessful) {
        try {
            Optional<User> userOptional = userRepository.findByMsisdn(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                int failedAttempts = authSuccessful ? 0 : (user.getSuccessiveFailedAttempts() + 1);
                user.setSuccessiveFailedAttempts(failedAttempts);
                userRepository.save(user);

                if (failedAttempts >= 3) {
                    blockUser(user);
                }
            }
        } catch (UsernameNotFoundException ex) {
            log.debug(ex.getMessage());
            throw new UsernameNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

    public void blockUser(User user) {
        user = userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(()->
                        new ServiceException(100,
                                "user not found"));
        user.setUserStatus(UserStatus.BLOCKED);

        userRepository.save(user);
    }

    public long userCount(){
        return userRepository.count();
    }

    public User signInUser(SignInDto signInDto) {

        UserRepresentation userRepresentation = keycloakService.authenticateUser(signInDto);
        if(userRepresentation != null){
            User user = getUserByKeyCloakId(userRepresentation.getId());
            user.setLastLogin(LocalDateTime.now());
            user.setLastSeen(LocalDateTime.now());
            user.setResetPassword(false);
            user.setUserStatus(UserStatus.CLEARED);
            userRepository.save(user);
            return user;
        }
        return null;
    }

    private WalletDto createWallet(SignupDto signupDto) {

        CreateWalletDto createWalletDto = CreateWalletDto.builder()
                .walletId(signupDto.getPhone())
                .password(signupDto.getPassword())
                .balance(BigDecimal.valueOf(0.0))
                .currency("GHS")
                .build();

        ApiResponse<?> createWalletResponse = webClientBuilder.build().post()
                .uri("lb://payment-service/api/payment/wallet")
                .body(Mono.just(createWalletDto) , CreateWalletDto.class)
                .exchangeToMono(clientResponse -> {
                    if(clientResponse.statusCode().is2xxSuccessful()){
                        return clientResponse.bodyToMono(new ParameterizedTypeReference<ApiResponse<WalletDto>>() {});
                    }else{
                        throw new ServiceException(WALLET_CREATION_EXCEPTION);
                    }
                })
                .block();
       return (WalletDto) Objects.requireNonNull(createWalletResponse).getData();
    }


}

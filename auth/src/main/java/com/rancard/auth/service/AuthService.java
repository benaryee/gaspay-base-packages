package com.rancard.auth.service;

import com.rancard.auth.exception.AuthException;
import com.rancard.auth.exception.ServiceException;
import com.rancard.auth.model.AgentRepository;
import com.rancard.auth.model.UserRepository;
import com.rancard.auth.model.domain.AuthUserDetail;
import com.rancard.auth.model.dto.*;
import com.rancard.auth.model.dto.wallet.CreateWalletDto;
import com.rancard.auth.model.dto.wallet.WalletDto;
import com.rancard.auth.model.enums.Channel;
import com.rancard.auth.model.enums.UserStatus;
import com.rancard.auth.model.mongo.Agent;
import com.rancard.auth.model.mongo.Role;
import com.rancard.auth.model.mongo.User;
import com.rancard.auth.model.payload.Address;
import com.rancard.auth.model.response.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

import static com.rancard.auth.model.enums.ServiceError.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakService keycloakService;
    private final UserService userService;

    private final RestTemplate restTemplate;

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

        UserRepresentation keycloakUser = keycloakService.registerUser(signUpUserDto);

        if(signUpUserDto.getChannel() != Channel.USSD && signUpUserDto.getAddress() != null && signUpUserDto.getAddress().getGhanaPostGps() != null){
            Address address = userService.getUserAddressByGps(signUpUserDto.getAddress().getGhanaPostGps());

            if(address != null){
                signUpUserDto.setAddress(address);
            }
        }

        if(signUpUserDto.getChannel().equals(Channel.USSD) || signUpUserDto.getChannel().equals(Channel.APP)) {
            User user = new User();
            user.setUsername(signUpUserDto.getUsername());
            user.setEmail(signUpUserDto.getEmail());
            user.setMsisdn(signUpUserDto.getPhone());
            user.setFirstname(signUpUserDto.getFirstName());
            user.setLastname(signUpUserDto.getLastName());
            user.setOthernames(signUpUserDto.getOtherNames());
            user.setKeycloakUserId(keycloakUser.getId());
            user.setUserStatus(UserStatus.CLEARED);
            user.setPassword(passwordEncoder.encode(signUpUserDto.getPassword()));
            user.setLastLogin(LocalDateTime.now());
            user.setLastSeen(LocalDateTime.now());
            user.setAddress(signUpUserDto.getAddress());

            if (signUpUserDto.getChannel().equals(Channel.USSD)) {
                WalletDto walletDto = createWallet(signUpUserDto);
                user.setWalletId(walletDto.getWalletId());
            }


            user.setFamilySize(signUpUserDto.getFamilySize());
            user.setCurrentFuelSource(signUpUserDto.getCurrentFuelSource());
            user.setUserStatus(UserStatus.CLEARED);

            log.info("user: {}", user);
            return userRepository.save(user);
        }else if(signUpUserDto.getChannel().equals(Channel.WEB)){
            Agent agent = new Agent();
            agent.setUsername(signUpUserDto.getUsername());
            agent.setEmail(signUpUserDto.getEmail());
            agent.setMsisdn(signUpUserDto.getPhone());
            agent.setFirstname(signUpUserDto.getFirstName());
            agent.setLastname(signUpUserDto.getLastName());
            agent.setOthernames(signUpUserDto.getOtherNames());
            agent.setKeycloakUserId(keycloakUser.getId());
            agent.setRoles(Collections.singleton(roleService.getRole(200)));
            agent.setPassword(passwordEncoder.encode(signUpUserDto.getPassword()));
            agent.setLastLogin(LocalDateTime.now());
            agent.setLastSeen(LocalDateTime.now());
            agent.setAddress(signUpUserDto.getAddress());


            WalletDto walletDto = createWallet(signUpUserDto);
            agent.setWalletId(walletDto.getWalletId());

            agent.setUserStatus(UserStatus.CLEARED);

            log.info("user: {}", agent);
            return agentRepository.save(agent);

        }else{
            Agent agent = new Agent();
            agent.setUsername(signUpUserDto.getUsername());
            agent.setEmail(signUpUserDto.getEmail());
            agent.setMsisdn(signUpUserDto.getPhone());
            agent.setFirstname(signUpUserDto.getFirstName());
            agent.setLastname(signUpUserDto.getLastName());
            agent.setOthernames(signUpUserDto.getOtherNames());
            agent.setKeycloakUserId(keycloakUser.getId());
            agent.setRoles(Collections.singleton(roleService.getRole(300)));
            agent.setPassword(passwordEncoder.encode(signUpUserDto.getPassword()));
            agent.setLastLogin(LocalDateTime.now());
            agent.setLastSeen(LocalDateTime.now());
            agent.setAddress(signUpUserDto.getAddress());


            WalletDto walletDto = createWallet(signUpUserDto);
            agent.setWalletId(walletDto.getWalletId());

            agent.setUserStatus(UserStatus.CLEARED);

            log.info("user: {}", agent);
            return agentRepository.save(agent);

        }

    }



    public User updateUser(EditUserDto editUserDto) {

        List<Role> userRoles = new ArrayList<>();
        editUserDto.getRoles().forEach(roleDto -> userRoles.add(roleService.getRole(roleDto.getCode())));

        User user = getUser(editUserDto.getId());
        user.setMsisdn(editUserDto.getPhone());
        user.setFirstname(editUserDto.getFirstName());
        user.setLastname(editUserDto.getLastName());
        user.setOthernames(editUserDto.getOtherNames());


        return userRepository.save(user);
    }

    public User removeUserElevatedRoles(String userId) {

        User user = getUser(userId);
        Role role = roleService.getRole(100);
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        User user = getUser(id);
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

    public AccessTokenResponse signInUser(SignInDto signInDto) {
        return keycloakService.authenticateUser(signInDto);
    }

    private WalletDto createWallet(SignupDto signupDto) {

        CreateWalletDto createWalletDto = CreateWalletDto.builder()
                .walletId(signupDto.getPhone())
                .password(signupDto.getPassword())
                .balance(BigDecimal.valueOf(0.0))
                .currency("GHS")
                .build();

        ApiResponse<WalletDto> createWalletResponse = new ApiResponse<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ParameterizedTypeReference<ApiResponse<WalletDto>> responseType = new ParameterizedTypeReference<ApiResponse<WalletDto>>() {};

            HttpEntity<CreateWalletDto> requestEntity = new HttpEntity<>(createWalletDto, headers);
            ResponseEntity<ApiResponse<WalletDto>> createWalletResponseEntity = restTemplate
                    .exchange("https://api.gaspayapp.com/api/payment/wallet",
                        HttpMethod.POST,
                            requestEntity,
                            responseType);
            createWalletResponse =  createWalletResponseEntity.getBody();
        }catch(Exception e){
            e.printStackTrace();
        }
       return Objects.requireNonNull(createWalletResponse).getData();
    }


}

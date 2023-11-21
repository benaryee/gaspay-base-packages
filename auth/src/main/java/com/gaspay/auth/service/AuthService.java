package com.gaspay.auth.service;

import com.gaspay.auth.exception.AuthException;
import com.gaspay.auth.exception.ServiceException;
import com.gaspay.auth.model.AgentRepository;
import com.gaspay.auth.model.RoleRepository;
import com.gaspay.auth.model.UserRepository;
import com.gaspay.auth.model.domain.AuthUserDetail;
import com.gaspay.auth.model.dto.*;
import com.gaspay.auth.model.dto.wallet.CreateWalletDto;
import com.gaspay.auth.model.dto.wallet.WalletDto;
import com.gaspay.auth.model.enums.Channel;
import com.gaspay.auth.model.enums.UserStatus;
import com.gaspay.auth.model.mongo.Agent;
import com.gaspay.auth.model.mongo.Role;
import com.gaspay.auth.model.mongo.User;
import com.gaspay.auth.model.payload.Address;
import com.gaspay.auth.model.response.response.AccessTokenResponseDto;
import com.gaspay.auth.model.response.response.ApiResponse;
import com.gaspay.auth.model.response.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.keycloak.representations.idm.UserRepresentation;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.gaspay.auth.model.enums.ServiceError.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AgentRepository agentRepository;
    private final RoleRepository roleRepository;
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

            log.info("[{}] about to add address", signUpUserDto.toString());

            Address address = userService.getUserAddressByGps(signUpUserDto.getAddress().getGhanaPostGps());

            if(address != null){
                signUpUserDto.setAddress(address);
            }
        }

        if(signUpUserDto.getChannel().equals(Channel.USSD) || signUpUserDto.getChannel().equals(Channel.APP)) {
            log.info("[{}] about to create user", signUpUserDto.toString());

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

            log.info("[{}] about to create agent", signUpUserDto.toString());

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

    public AuthResponse signInUser(SignInDto signInDto) {
        AccessTokenResponseDto accessTokenResponse = keycloakService.authenticateUser(signInDto);
        AuthResponse authResponse = new AuthResponse();
        UserDto userDto = null;

        if(accessTokenResponse.getToken() != null){


            if(accessTokenResponse.getRoles().contains("USER")){
                User user = userRepository.findByMsisdn(signInDto.getUsername()).orElse(null);
                if (user != null) {
                    user.setLastLogin(LocalDateTime.now());
                    userRepository.save(user);

                    userDto = UserDto.builder()
                            .firstname(user.getFirstname())
                            .lastname(user.getLastname())
                            .email(user.getEmail())
                            .phone(user.getMsisdn())
                            .build();

                }
            }else if(accessTokenResponse.getToken().contains("VENDOR") || accessTokenResponse.getToken().contains("AGENT")){
                Agent user = agentRepository.findByMsisdn(signInDto.getUsername()).orElse(null);
                if (user != null) {
                    user.setLastLogin(LocalDateTime.now());
                    agentRepository.save(user);
                    userDto = UserDto.builder()
                            .firstname(user.getFirstname())
                            .lastname(user.getLastname())
                            .email(user.getEmail())
                            .phone(user.getMsisdn())
                            .build();
                }
            }
            authResponse.setUser(userDto);
            authResponse.setToken(accessTokenResponse.getToken());
            return authResponse;
        }
        throw new ServiceException(USER_NOT_FOUND);

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


    public void setTestData() {
        Role role  = Role.builder()
                .code(300)
                .name("AGENT")
                .description("Agent")
                .build();
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);

        Agent agent = Agent.builder()
                .firstname("Nii")
                .lastname("Ayi")
                .msisdn("233548410151")
                .outletName("Rancard")
                .roles(roles)
                .build();
        agentRepository.save(agent);

        if (roleRepository.count() < 1) {
            roleRepository.saveAll(List.of(
                    new Role(100, "USER", "User Role"),
                    new Role(200,"VENDOR", "Vendor Role"),
                    new Role(300, "AGENT", "Agent Role")
            ));

        }
    }
}

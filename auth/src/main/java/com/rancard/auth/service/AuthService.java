package com.rancard.auth.service;

import com.rancard.auth.exception.AuthException;
import com.rancard.auth.exception.ServiceException;
import com.rancard.auth.model.UserRepository;
import com.rancard.auth.model.domain.AuthUserDetail;
import com.rancard.auth.model.dto.EditPasswordDto;
import com.rancard.auth.model.dto.EditUserDto;
import com.rancard.auth.model.dto.SignInDto;
import com.rancard.auth.model.dto.SignupDto;
import com.rancard.auth.model.enums.UserStatus;
import com.rancard.auth.model.mongo.Role;
import com.rancard.auth.model.mongo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import java.time.LocalDateTime;

import org.keycloak.representations.idm.UserRepresentation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final KeycloakService keycloakService;
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

        Optional<User> optionalUser = userRepository.findUserByEmail(signUpUserDto.getEmail());

        if (optionalUser.isPresent())
            throw new ServiceException(100,
                    "user email already exist");

        List<Role> userRoles = new ArrayList<>();
        signUpUserDto.getRoles().forEach(roleDto -> userRoles.add(roleService.getRole(roleDto.getCode())));

        UserRepresentation keycloakUser = keycloakService.registerUser(signUpUserDto);

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
        user.getAddress().setGhanaPostGps(signUpUserDto.getGhanaPostGps());

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
}

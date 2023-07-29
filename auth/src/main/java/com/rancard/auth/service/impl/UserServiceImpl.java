package com.rancard.auth.service.impl;


import com.querydsl.core.types.Predicate;
import com.rancard.auth.exception.AuthException;
import com.rancard.auth.exception.ServiceException;
import com.rancard.auth.models.domain.AuthUserDetail;
import com.rancard.auth.models.dto.EditPasswordDto;
import com.rancard.auth.models.dto.EditUserDto;
import com.rancard.auth.models.dto.SignupDto;
import com.rancard.auth.models.enums.UserStatus;
import com.rancard.auth.models.mongo.Role;
import com.rancard.auth.models.mongo.User;
import com.rancard.auth.models.repository.UserRepository;
import com.rancard.auth.service.KeycloakService;
import com.rancard.auth.service.UserService;
import com.rancard.auth.service.UserStatusManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;


@Service
@CacheConfig(cacheNames = {"users"})
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService, UserStatusManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleService;

    private final KeycloakService keycloakService;
    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ServiceException(100,
                        "user not found by id: " + id ));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() ->
                new ServiceException(100,
                        "user not found by username: " + username ));
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.findByMsisdn(phone).orElseThrow(() ->
                new ServiceException(100,
                        "user not found by phone: " + phone ));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new ServiceException(100,
                        "user not found by email: " + email ));
    }

    @Override
    public Page<User> getAllUsers(Predicate spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public List<User> getUserByIds(List<String> userIds) {
        Iterable<User> allById = userRepository.findAllById(userIds);

        List<User> foundUsers = new ArrayList<>();
        allById.forEach(foundUsers::add);

        return foundUsers;
    }

    @Override
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

        UserRepresentation userRepresentation = keycloakService.registerUser(signUpUserDto.getUsername() , signUpUserDto.getEmail() , signUpUserDto.getPassword());

        log.info("userRepresentation: {}", userRepresentation);

        User user = new User();
        user.setUsername(signUpUserDto.getUsername());
        user.setEmail(signUpUserDto.getEmail());
        user.setMsisdn(signUpUserDto.getPhone());
        user.setFirstname(signUpUserDto.getFirstName());
        user.setLastname(signUpUserDto.getLastName());
        user.setOthernames(signUpUserDto.getOtherNames());

        user.setUserStatus(UserStatus.CLEARED);
        user.setPassword(passwordEncoder.encode(signUpUserDto.getPassword()));
        user.setLastLogin(new Timestamp(new Date().getTime()));
        user.setLastSeen(new Timestamp(new Date().getTime()));

        user.setUserStatus(UserStatus.CLEARED);
        user.setRoles(new HashSet<>(userRoles));

        return userRepository.save(user);
    }

    @Override
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

    @Override
    public User removeUserElevatedRoles(String userId) {

        User user = getUser(userId);
        Role role = roleService.getRole(100);
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = getUser(id);
        user.getRoles().removeAll(user.getRoles());
        userRepository.delete(user);
    }

    @Override
    public void updateUserPassword(EditPasswordDto editPasswordDto) {
        User user = getUser(editPasswordDto.getId());

        if (user.isResetPassword())
            user.setPassword(passwordEncoder.encode(editPasswordDto.getPassword()));
        userRepository.save(user);
    }

    @Override
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

    @Override
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

    @Override
    public void blockUser(User user) {
        user = userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(()->
                        new ServiceException(100,
                                "user not found"));
        user.setUserStatus(UserStatus.BLOCKED);

        userRepository.save(user);
    }

    @Override
    public long userCount(){
        return userRepository.count();
    }
}
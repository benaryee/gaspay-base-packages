package com.rancard.ussdapp.services;

import com.rancard.ussdapp.model.dao.mongo.UserDao;
import com.rancard.ussdapp.model.enums.Role;
import com.rancard.ussdapp.model.mongo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public User findUserByMsisdn(String msisdn, String sessionId){
        log.info("[{}] about to fetch user with msisdn : {}",sessionId , msisdn );
        return userDao.findByMsisdn(msisdn).orElse(null);
    }

    public User registerUser(User user, String sessionId){
        log.info("[{}] about to register user on platform user with details : {}",sessionId , user.toString() );
        User newUser = User.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .msisdn(user.getMsisdn())
                .currentFuelSource(user.getCurrentFuelSource())
                .address(user.getAddress())
                .familySize(user.getFamilySize())
                .role(Role.CUSTOMER)
                .build();

        return userDao.save(newUser);
    }
}

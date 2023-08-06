package com.rancard.auth.service;

import com.rancard.auth.exception.ServiceException;
import com.rancard.auth.model.UserRepository;
import com.rancard.auth.model.dto.UserDto;
import com.rancard.auth.model.mongo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.rancard.auth.model.enums.ServiceError.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByMsisdn(String msisdn){
        return userRepository.findByMsisdn(msisdn).orElseThrow(()->
                new ServiceException(USER_NOT_FOUND));
    }

}

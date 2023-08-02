package com.rancard.ussdapp.services;

import com.rancard.ussdapp.model.dao.mongo.UserDao;
import com.rancard.ussdapp.model.dto.SignupDto;
import com.rancard.ussdapp.model.mongo.User;
import com.rancard.ussdapp.model.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final WebClient.Builder webClientBuilder;


    public User findUserByMsisdn(String msisdn, String sessionId){
        log.info("[{}] about to fetch user with msisdn : {}",sessionId , msisdn );
        return userDao.findByMsisdn(msisdn).orElse(null);
    }

    public User registerUser(User user, String sessionId){
        log.info("[{}] about to register user on platform user with details : {}",sessionId , user.toString() );
        SignupDto signupDto = SignupDto.builder()
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .phone(user.getMsisdn())
                .currentFuelSource(user.getCurrentFuelSource())
                .ghanaPostGps(user.getAddress().getGhanaPostGps())
                .password(user.getPassword())
                .familySize(user.getFamilySize())
                .build();

        ApiResponse signUpResponse = webClientBuilder.build().post()
                .uri("http://auth/api/signup")
                .body(Mono.just(signupDto) , SignupDto.class)
                .exchangeToMono(clientResponse -> {
                    if(clientResponse.statusCode().is2xxSuccessful()){
                        return clientResponse.bodyToMono(ApiResponse.class);
                    }else{
                        return clientResponse.bodyToMono(ApiResponse.class);
                    }
                })
                .block();

        log.info("[{}] response from auth service : {}", sessionId , signUpResponse != null ? signUpResponse.toString() : null);
        return user;
    }

    public int getTotalNumberOfUsers(boolean exists){
        return userDao.countAllByMsisdnExists(exists);
    }
}

package com.rancard.ussdapp.services;

import com.rancard.ussdapp.model.dao.mongo.UserDao;
import com.rancard.ussdapp.model.dto.RoleDto;
import com.rancard.ussdapp.model.dto.SignupDto;
import com.rancard.ussdapp.model.dto.UserDto;
import com.rancard.ussdapp.model.enums.Channel;
import com.rancard.ussdapp.model.mongo.User;
import com.rancard.ussdapp.model.payload.Address;
import com.rancard.ussdapp.model.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final WebClient.Builder webClientBuilder;


    public UserDto findUserByMsisdn(String msisdn, String sessionId){
        log.info("[{}] about to fetch user with msisdn : {}",sessionId , msisdn );

        ApiResponse<?> signInResponse = webClientBuilder.build().get()
                .uri("lb://auth/api/auth/users/{msisdn}", msisdn)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserDto>>() {})
                .block();

        if(signInResponse != null && signInResponse.getData() != null){
            return (UserDto) signInResponse.getData();
        }
        return null;
    }

    public Address findAddressByGps(String ghanaPostGps, String sessionId){
        log.info("[{}] about to fetch user with ghanaPostGps : {}",sessionId , ghanaPostGps );

        ApiResponse<?> addressApiResponse = webClientBuilder.build().get()
                .uri("lb://auth/api/auth/users/gps/{ghanaPostGps}", ghanaPostGps)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Address>>() {})
                .block();

        if(addressApiResponse != null && addressApiResponse.getData() != null){
            return (Address) addressApiResponse.getData();
        }

        return null;
    }

    public UserDto registerUser(UserDto user, String sessionId){
        log.info("[{}] about to register user on platform user with details : {}",sessionId , user.toString() );
        RoleDto roleDto = RoleDto.builder()
                .name("USER")
                .code(100)
                .build();

        SignupDto signupDto = SignupDto.builder()
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .username(user.getFirstname() + " " + user.getLastname())
                .phone(user.getPhone())
                .currentFuelSource(user.getCurrentFuelSource())
                .address(user.getAddress())
                .password(user.getPassword())
                .confirmPassword(user.getConfirmPassword())
                .roles(Collections.singletonList(roleDto))
                .familySize(user.getFamilySize())
                .channel(Channel.USSD)
                .build();

        ApiResponse<?> signUpResponse = webClientBuilder.build().post()
                .uri("lb://auth/api/auth/signup")
                .body(Mono.just(signupDto) , SignupDto.class)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(ApiResponse.class))
                .block();

        log.info("[{}] response from auth service : {}", sessionId , signUpResponse != null ? signUpResponse.toString() : null);
        return user;
    }

    public int getTotalNumberOfUsers(boolean exists){
        return userDao.countAllByMsisdnExists(exists);
    }
}

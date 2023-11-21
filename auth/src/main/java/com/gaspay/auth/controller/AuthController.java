package com.gaspay.auth.controller;

import com.gaspay.auth.model.dto.SignInDto;
import com.gaspay.auth.model.dto.SignupDto;
import com.gaspay.auth.model.dto.UserDto;
import com.gaspay.auth.model.mongo.User;
import com.gaspay.auth.model.response.response.ApiResponse;
import com.gaspay.auth.model.response.response.AuthResponse;
import com.gaspay.auth.service.AuthService;
import com.gaspay.auth.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    public ApiResponse<?> signUpUser(@RequestBody SignupDto signupDto,
                                           HttpServletRequest httpServletRequest) {

        String sessionId = httpServletRequest.getSession().getId();

        log.info("[{}] http request: signUpUser with details :{}",sessionId, signupDto);

        User user = authService.signUpUser(signupDto);
        UserDto userDto = modelMapper.map(user, UserDto.class);

        ApiResponse<UserDto> apiResponse= ApiUtils.wrapInApiResponse(userDto, sessionId);

        log.info("[{}] http response: signUpUser: {}",sessionId, apiResponse);
        return apiResponse;
    }

    @PostMapping("/signin")
    public ApiResponse<?> signIn(@RequestBody SignInDto signInDto,
                                           HttpServletRequest httpServletRequest) {

        String sessionId = httpServletRequest.getSession().getId();

        log.info("[{}] http request: signInUser with details :{}",sessionId, signInDto);

        AuthResponse authResponse = authService.signInUser(signInDto);

        if(authResponse == null){
            throw  HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, "Invalid credentials", null, null, null);
        }
//
        log.info("[{}] http response: signInUser: {}",sessionId, authResponse);
        return ApiUtils.wrapInApiResponse(authResponse, sessionId);
    }


    @GetMapping
    public void getRoles() {
        authService.getAllRoles();
    }


    @GetMapping("/test/data")
    public void setTestData() {
        authService.setTestData();
    }


}


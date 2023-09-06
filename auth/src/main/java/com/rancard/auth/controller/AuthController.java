package com.rancard.auth.controller;

import com.rancard.auth.model.dto.SignInDto;
import com.rancard.auth.model.dto.SignupDto;
import com.rancard.auth.model.dto.UserDto;
import com.rancard.auth.model.mongo.User;
import com.rancard.auth.model.response.response.ApiResponse;
import com.rancard.auth.service.AuthService;
import com.rancard.auth.utils.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.HttpClientErrorException;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> signIn(@RequestBody SignInDto signInDto,
                                           HttpServletRequest httpServletRequest) {

        String sessionId = httpServletRequest.getSession().getId();

        log.info("[{}] http request: signInUser with details :{}",sessionId, signInDto);

        User user = authService.signInUser(signInDto);

        if(user == null){
            throw  HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, "Invalid credentials", null, null, null);
        }

        ApiResponse<User> apiResponse= ApiUtils.wrapInApiResponse(user, sessionId);

        log.info("[{}] http response: signInUser: {}",sessionId, apiResponse);
        return apiResponse;
    }


    @GetMapping
    public void getRoles() {
        authService.getAllRoles();
    }
}


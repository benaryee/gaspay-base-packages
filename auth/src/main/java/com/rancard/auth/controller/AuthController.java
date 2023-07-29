package com.rancard.auth.controller;

import com.rancard.auth.models.dto.SignupDto;
import com.rancard.auth.models.dto.UserDto;
import com.rancard.auth.models.mongo.User;
import com.rancard.auth.models.response.response.ApiResponse;
import com.rancard.auth.service.UserService;
import com.rancard.auth.utils.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/sign_up")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDto> signUpUser(@RequestBody @Valid SignupDto signupDto,
                                           HttpServletRequest httpServletRequest) {

        String sessionId = httpServletRequest.getSession().getId();

        log.info("[{}] http request: signUpUser with details :{}",sessionId, signupDto);

        User user = userService.signUpUser(signupDto);
        UserDto userDto = modelMapper.map(user, UserDto.class);

        ApiResponse<UserDto> apiResponse= ApiUtils.wrapInApiResponse(userDto, sessionId);

        log.info("[{}] http response: signUpUser: {}",sessionId, apiResponse);
        return apiResponse;
    }
}

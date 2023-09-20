package com.rancard.auth.controller;

import com.rancard.auth.model.dto.UserDto;
import com.rancard.auth.model.mongo.User;
import com.rancard.auth.model.payload.Address;
import com.rancard.auth.model.response.response.ApiResponse;
import com.rancard.auth.service.UserService;
import com.rancard.auth.utils.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/auth/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/{msisdn}")
    public ApiResponse<?> getUserByMsisdn(@PathVariable String msisdn, HttpServletRequest request){
        String sessionId = request.getSession().getId();
         User user = userService.getUserByMsisdn(msisdn);

        UserDto userDto = modelMapper.map(user, UserDto.class);
        ApiResponse<UserDto> apiResponse = ApiUtils.wrapInApiResponse(userDto, sessionId);

        log.info("[{}] http response: getRoleByCode: {}", sessionId, apiResponse);

        return apiResponse;
    }

    @GetMapping("/gps/{ghanaPostGps}")
    public ApiResponse<?> getUserAddress(@PathVariable String ghanaPostGps, HttpServletRequest request){
        String sessionId = request.getSession().getId();
        Address address = userService.getUserAddressByGps(ghanaPostGps);

        Address userAddress = modelMapper.map(address, Address.class);

        return ApiUtils.wrapInApiResponse(userAddress, sessionId);
    }

}

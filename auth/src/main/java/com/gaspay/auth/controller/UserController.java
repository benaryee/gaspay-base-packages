package com.gaspay.auth.controller;

import com.gaspay.auth.model.dto.UserDto;
import com.gaspay.auth.model.mongo.Agent;
import com.gaspay.auth.model.mongo.User;
import com.gaspay.auth.model.payload.Address;
import com.gaspay.auth.model.response.response.ApiResponse;
import com.gaspay.auth.service.UserService;
import com.gaspay.auth.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/agent")
    public ApiResponse<?> getAgents(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        Agent agent = userService.getAgents();
        return ApiUtils.wrapInApiResponse(agent, sessionId);
    }


}

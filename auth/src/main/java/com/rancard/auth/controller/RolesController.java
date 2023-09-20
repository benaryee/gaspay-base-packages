package com.rancard.auth.controller;


import com.rancard.auth.model.dto.RoleDto;
import com.rancard.auth.model.mongo.Role;
import com.rancard.auth.model.response.response.ApiResponse;
import com.rancard.auth.model.response.response.PagedContent;
import com.rancard.auth.service.RoleService;
import com.rancard.auth.utils.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth/role")
public class RolesController {

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public ApiResponse<?> getAllRRoles(Pageable pageable, HttpServletRequest httpServletRequest){

        String sessionId = httpServletRequest.getSession().getId();

        log.info("[{}] http request: getAllRRoles",sessionId);

        Page<Role> allRoles = roleService.getAllRoles(pageable);
        List<RoleDto> roleDtos = allRoles
                .stream().
                map(role -> modelMapper.map(role, RoleDto.class))
                .collect(Collectors.toList());

        ApiResponse<PagedContent<RoleDto>> apiResponse= ApiUtils.wrapInPagedApiResponse(allRoles, roleDtos, sessionId);


        log.info("[{}] http response: getAllRRoles: {}", sessionId, apiResponse);

        return apiResponse;
    }

    @PostMapping
    public ApiResponse<?> createRole(@RequestBody RoleDto roleDto, HttpServletRequest httpServletRequest) {

        String sessionId = httpServletRequest.getSession().getId();

        log.info("[{}] http request: create new role {} ", sessionId, roleDto);

        Role role = roleService.createRole(roleDto);
        roleDto = modelMapper.map(role, RoleDto.class);

        ApiResponse<RoleDto> apiResponse = ApiUtils.wrapInApiResponse(roleDto, sessionId);

        log.info("[{}] http response: create role: {}", sessionId, apiResponse);

        return apiResponse;
    }

    @GetMapping(value = "/{code}")
    public ApiResponse<?> getRoleByCode(@PathVariable int code,
                                              HttpServletRequest httpServletRequest) {

        String sessionId = httpServletRequest.getSession().getId();

        log.info("[{}] http request: getRoleByCode: {} ", sessionId, code);

        Role role = roleService.getRole(code);
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        ApiResponse<RoleDto> apiResponse = ApiUtils.wrapInApiResponse(roleDto, sessionId);

        log.info("[{}] http response: getRoleByCode: {}", sessionId, apiResponse);

        return apiResponse;
    }
}

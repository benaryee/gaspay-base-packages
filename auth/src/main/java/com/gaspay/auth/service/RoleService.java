package com.gaspay.auth.service;

import com.gaspay.auth.exception.ServiceException;
import com.gaspay.auth.model.RoleRepository;
import com.gaspay.auth.model.dto.RoleDto;
import com.gaspay.auth.model.mongo.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role getRole(int code) {
        return roleRepository.findByCode(code).orElseThrow(()->
                new ServiceException(102, "no role found for: " + code));
    }

    @Transactional
    public Page<Role> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role createRole(RoleDto roleDto) {
        log.info("creating role: {}", roleDto);
        Role role = Role.builder()
                .code(roleDto.getCode())
                .name(roleDto.getName())
                .description(roleDto.getDescription())
                .build();
       return roleRepository.save(role);
    }
}

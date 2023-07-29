package com.rancard.auth.service.impl;


import com.querydsl.core.types.Predicate;
import com.rancard.auth.exception.ServiceException;
import com.rancard.auth.models.mongo.Role;
import com.rancard.auth.models.repository.RoleRepository;
import com.rancard.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RefreshScope
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public Role getRole(int code) {
    return roleRepository.findByCode(code).orElseThrow(()->
                new ServiceException(102, "no role found for: " + code));
    }

    @Override
    @Transactional
    public Page<Role> getAllRoles(Predicate spec, Pageable pageable) {
        return roleRepository.findAll(spec, pageable);
    }
}

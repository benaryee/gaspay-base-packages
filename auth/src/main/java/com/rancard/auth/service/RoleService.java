package com.rancard.auth.service;

import com.querydsl.core.types.Predicate;
import com.rancard.auth.models.mongo.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RoleService {
    Role getRole(int code);

    Page<Role> getAllRoles(Predicate spec, Pageable pageable);
}

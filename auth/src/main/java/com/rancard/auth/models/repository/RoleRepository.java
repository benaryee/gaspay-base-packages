package com.rancard.auth.models.repository;

import com.rancard.auth.models.mongo.Role;
import com.rancard.auth.models.mongo.QRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String>, QuerydslPredicateExecutor<Role>, QuerydslBinderCustomizer<QRole> {
    Optional<Role> findByCode(int code);
}

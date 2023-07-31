package com.rancard.auth.model;

import com.querydsl.core.types.dsl.StringPath;
import com.rancard.auth.model.mongo.QRole;
import com.rancard.auth.model.mongo.Role;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String>, QuerydslPredicateExecutor<Role> , QuerydslBinderCustomizer<QRole> {
    Optional<Role> findByCode(int code);

    @Override
    default void customize(QuerydslBindings bindings, QRole role) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));

    }

}

package com.rancard.auth.model;

import com.rancard.auth.model.mongo.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String>{
    Optional<Role> findByCode(int code);

}

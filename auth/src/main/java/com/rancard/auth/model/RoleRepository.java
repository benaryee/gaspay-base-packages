package com.rancard.auth.model;

import com.rancard.auth.model.mongo.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends MongoRepository<Role, String>{
    Optional<Role> findByCode(int code);

}

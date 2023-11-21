package com.gaspay.auth.model;

import com.gaspay.auth.model.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByKeycloakUserId(String keycloakUserId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findByMsisdn(String phone);

    User findByUsernameOrEmailOrMsisdn(String username, String email, String phone);
}

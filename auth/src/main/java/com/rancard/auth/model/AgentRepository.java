package com.rancard.auth.model;

import com.rancard.auth.model.mongo.Agent;
import com.rancard.auth.model.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends MongoRepository<Agent, String> {
    Optional<Agent> findUserByUsername(String username);
    Optional<Agent> findUserByKeycloakUserId(String keycloakUserId);
    Optional<Agent> findUserByEmail(String email);
    Optional<Agent> findByMsisdn(String phone);
    Agent findTopByActiveIsTrue();
    Agent findByUsernameOrEmailOrMsisdn(String username, String email, String phone);
}

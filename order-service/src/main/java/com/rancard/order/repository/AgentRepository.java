package com.rancard.order.repository;

import com.rancard.order.model.mongo.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends MongoRepository<Agent, String> {
}

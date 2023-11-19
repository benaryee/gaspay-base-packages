package com.rancard.order.repository;

import com.rancard.basepackages.mongo.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerMsisdn(String customerMsisdn);
    List<Order> findByAgentId(String agentId);

    Order findByOrderId(String orderId);
}

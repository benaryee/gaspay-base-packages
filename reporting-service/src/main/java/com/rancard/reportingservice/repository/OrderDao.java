package com.rancard.reportingservice.repository;


import com.rancard.basepackages.mongo.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderDao extends MongoRepository<Order,String> {

    List<Order> findByCustomerMsisdn(String customerMsisdn);
    List<Order> findByAgentId(String agentId);

    Order findByOrderId(String orderId);
}

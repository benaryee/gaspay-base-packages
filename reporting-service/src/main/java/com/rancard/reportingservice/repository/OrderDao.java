package com.rancard.reportingservice.repository;

import com.rancard.reportingservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderDao extends MongoRepository<Order,String> {
}

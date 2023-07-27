package com.rancard.orderservice.repository;

import com.rancard.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}

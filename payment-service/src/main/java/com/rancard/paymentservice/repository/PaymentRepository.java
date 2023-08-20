package com.rancard.paymentservice.repository;

import com.rancard.paymentservice.model.mongo.Payment;
import com.rancard.paymentservice.model.mongo.PaymentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findByPaymentId(String s);
}

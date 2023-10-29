package com.rancard.paymentservice.repository;

import com.rancard.paymentservice.model.mongo.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
    Optional<Wallet> findByWalletId(String s);
}

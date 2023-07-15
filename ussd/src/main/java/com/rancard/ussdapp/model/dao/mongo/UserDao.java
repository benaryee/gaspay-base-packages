package com.rancard.ussdapp.model.dao.mongo;

import com.rancard.ussdapp.model.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends MongoRepository<User,String> {


    Optional<User> findByMsisdn(String s);
}

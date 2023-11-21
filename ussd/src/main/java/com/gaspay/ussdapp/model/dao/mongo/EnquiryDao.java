package com.gaspay.ussdapp.model.dao.mongo;

import com.gaspay.ussdapp.model.mongo.Enquiry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnquiryDao extends MongoRepository<Enquiry , String> {
}

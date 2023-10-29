package com.rancard.ussdapp.model.dao.mongo;

import com.rancard.ussdapp.model.mongo.Enquiry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnquiryDao extends MongoRepository<Enquiry , String> {
}

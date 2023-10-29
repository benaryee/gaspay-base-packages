package com.rancard.ussdapp.services;

import com.rancard.ussdapp.model.dao.mongo.EnquiryDao;
import com.rancard.ussdapp.model.enums.Role;
import com.rancard.ussdapp.model.mongo.Enquiry;
import com.rancard.ussdapp.model.mongo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnquiryService {

    private final EnquiryDao enquiryDao;

    public Enquiry findEnquiryById(String id, String sessionId){
        log.info("[{}] about to fetch enquiry with id : {}",sessionId , id );
        return enquiryDao.findById(id).orElse(null);
    }

    public Enquiry makeEnquiry(Enquiry enquiry, String sessionId){
        log.info("[{}] about to save enquiry on platform user with details : {}",sessionId , enquiry.toString() );
        Enquiry newEnquiry = Enquiry.builder()
                .request(enquiry.getRequest())
                .alternativeNumber(enquiry.getAlternativeNumber())
                .msisdn(enquiry.getMsisdn())
                .callback(enquiry.isCallback())
                .build();

        return enquiryDao.save(newEnquiry);
    }
}

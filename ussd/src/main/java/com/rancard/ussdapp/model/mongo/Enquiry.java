package com.rancard.ussdapp.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enquiry {

    private String msisdn;
    private String request;
    private boolean callback;
    private String alternativeNumber;
    private boolean respondedTo;

}

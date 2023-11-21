package com.gaspay.ussdapp.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

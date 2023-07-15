package com.rancard.ussdapp.model.request;

import com.rancard.ussdapp.utils.MsisdnUtils;
import lombok.Data;

@Data
public class UssdRequest {

    private String sessionId;
    private String menu;
    private String msisdn;
    private String mobileNetwork;
    private String message;
    private String data;
    private String extension;

    public boolean isValidRequest(String sessionId) {

        this.msisdn = MsisdnUtils.phoneNumberFormat(msisdn,sessionId);

        return (this.menu != null
                && this.mobileNetwork != null
                && MsisdnUtils.phoneNumberFormat(msisdn,sessionId) != null
                && this.sessionId != null && !this.message.contains("UNKNOWN_ERROR"));
    }

}

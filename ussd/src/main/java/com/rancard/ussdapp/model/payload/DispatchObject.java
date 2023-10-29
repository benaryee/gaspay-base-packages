package com.rancard.ussdapp.model.payload;

import com.rancard.ussdapp.model.redis.Session;
import com.rancard.ussdapp.model.request.UssdRequest;
import lombok.Data;

@Data
public class DispatchObject {
    private Session session;
    private UssdRequest ussdRequest;
}

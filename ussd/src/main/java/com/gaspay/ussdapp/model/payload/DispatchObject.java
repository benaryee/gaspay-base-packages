package com.gaspay.ussdapp.model.payload;

import com.gaspay.ussdapp.model.redis.Session;
import com.gaspay.ussdapp.model.request.UssdRequest;
import lombok.Data;

@Data
public class DispatchObject {
    private Session session;
    private UssdRequest ussdRequest;
}

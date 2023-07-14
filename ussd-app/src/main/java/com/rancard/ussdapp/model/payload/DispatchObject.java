package com.rancard.ussdapp.model.payload;

import com.rancard.ussdapp.model.redis.Session;
import com.rancard.ussdapp.model.request.UssdRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchObject {
    private Session session;
    private UssdRequest ussdRequest;
}

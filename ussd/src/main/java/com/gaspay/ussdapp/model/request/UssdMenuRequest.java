package com.gaspay.ussdapp.model.request;

import com.gaspay.ussdapp.model.enums.MenuKey;
import com.gaspay.ussdapp.model.payload.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UssdMenuRequest {
    private MenuKey menuKey;
    private String response;
    private List<Option> options;
}

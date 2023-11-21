package com.gaspay.ussdapp.model.response;

import com.gaspay.ussdapp.model.enums.MenuLevel;
import com.gaspay.ussdapp.model.payload.Option;
import lombok.Data;

import java.util.List;

@Data
public class UssdMenuResponse {
    private MenuLevel menuLevel;
    private String response;
    private List<Option> options;
}

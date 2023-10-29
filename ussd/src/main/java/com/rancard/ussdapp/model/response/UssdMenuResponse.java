package com.rancard.ussdapp.model.response;

import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.payload.Option;
import lombok.Data;

import java.util.List;

@Data
public class UssdMenuResponse {
    private MenuLevel menuLevel;
    private String response;
    private List<Option> options;
}

package com.rancard.ussdapp.model.request;

import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.payload.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UssdMenuRequest {
    private MenuLevel menuLevel;
    private String response;
    private List<Option> options;
}
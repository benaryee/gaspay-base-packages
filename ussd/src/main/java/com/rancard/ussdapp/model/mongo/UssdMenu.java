package com.rancard.ussdapp.model.mongo;

import com.rancard.ussdapp.model.enums.MenuKey;
import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.payload.Option;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@Builder
public class UssdMenu {
    private String id;
    private MenuKey menuKey;
    private String response;
    private List<Option> options;

}

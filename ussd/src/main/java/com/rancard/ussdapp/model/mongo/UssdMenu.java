package com.rancard.ussdapp.model.mongo;

import com.rancard.ussdapp.model.enums.MenuKey;
import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.payload.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UssdMenu {
    private String id;
    private MenuKey menuKey;
    private String response;
    private List<Option> options;

    public UssdMenu(MenuKey menuKey, String response, List<Option> options) {
        this.menuKey = menuKey;
        this.response = response;
        this.options = options;
    }

    public UssdMenu(MenuKey menuKey, String response) {
        this.menuKey = menuKey;
        this.response = response;
    }


}

package com.rancard.ussdapp.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    private int id;
    private String content;
    private String handler;

    public Option(int id, String content) {
        this.id = id;
        this.content = content;
    }

}

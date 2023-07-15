package com.rancard.ussdapp.model.redis;


import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.mongo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RedisHash
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session implements Serializable {

    private String id;
    private User user;
    private MenuLevel menuLevel;
    private String pendingCode;
    private String uniqueCode;
    private String previousSelection;
    private String starCode;

    private String itemPrice;
    private boolean initialRequest;
    private Map<Integer, String> options = new HashMap<>();
    private boolean hasNext;
    private boolean hasBack;


    private int optionsCurrentPage;
    private int pageSize;
    private int optionsSize;
    private int pageLimit = 5;

    private boolean continueSession;

    public Session(String id, MenuLevel menuLevel) {
        this.id = id;
        this.menuLevel = menuLevel;
    }



}
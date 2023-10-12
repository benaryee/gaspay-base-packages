package com.rancard.ussdapp.model.redis;


import com.rancard.ussdapp.model.dto.CreateOrderDto;
import com.rancard.ussdapp.model.dto.UserDto;
import com.rancard.ussdapp.model.dto.WalletResponseDto;
import com.rancard.ussdapp.model.enums.MenuKey;
import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.enums.SubMenuLevel;
import com.rancard.ussdapp.model.mongo.Enquiry;

import com.rancard.ussdapp.model.payload.GenericValueMap;
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
    private UserDto user;
    private Enquiry enquiry;
    private WalletResponseDto wallet;
    private CreateOrderDto orderDto;
    private MenuLevel menuLevel;
    private SubMenuLevel subMenuLevel;
    private SubMenuLevel previousSubMenuLevel;
    private MenuKey menuKey;
    private String pendingCode;
    private String uniqueCode;
    private String previousSelection;
    private String starCode;
    private String topUpAmount;

    private String itemPrice;
    private boolean initialRequest;
    private GenericValueMap<?> options = new GenericValueMap<>();
    private GenericValueMap<?> previousOptions = new GenericValueMap<>();
    private boolean hasNext;
    private boolean hasBack;

    private boolean throwPreviousMenuError;

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

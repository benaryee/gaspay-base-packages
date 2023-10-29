package com.rancard.ussdapp.model.dao.mongo;

import com.rancard.ussdapp.model.enums.MenuKey;
import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.mongo.UssdMenu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UssdMenuDao extends MongoRepository<UssdMenu, String> {
    UssdMenu findByMenuKey(MenuKey menuKey);
}

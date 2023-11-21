package com.gaspay.ussdapp.model.dao.mongo;

import com.gaspay.ussdapp.model.enums.MenuKey;
import com.gaspay.ussdapp.model.mongo.UssdMenu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UssdMenuDao extends MongoRepository<UssdMenu, String> {
    UssdMenu findByMenuKey(MenuKey menuKey);
}

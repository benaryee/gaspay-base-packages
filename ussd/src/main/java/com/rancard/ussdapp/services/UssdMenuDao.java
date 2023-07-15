package com.rancard.ussdapp.services;

import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.mongo.UssdMenu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UssdMenuDao extends MongoRepository<UssdMenu, String> {
    UssdMenu findByMenuLevel(MenuLevel menuLevel);
}

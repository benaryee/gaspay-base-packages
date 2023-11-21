package com.gaspay.ussdapp.services;

import com.gaspay.ussdapp.model.dao.mongo.UssdMenuDao;
import com.gaspay.ussdapp.model.enums.MenuKey;
import com.gaspay.ussdapp.model.mongo.UssdMenu;
import com.gaspay.ussdapp.model.request.UssdMenuRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UssdMenuService {

    private final UssdMenuDao ussdMenuDao;

    public UssdMenu getMenuByLevel(MenuKey menuKey, String sessionId) {
        log.info("[{} fetching menu with level : {} ]", sessionId, menuKey);
        UssdMenu menu = ussdMenuDao.findByMenuKey(menuKey);
        log.info("[{}] returned menu: {} ", sessionId, menu.toString());
        return menu;
    }

    public UssdMenu addMenu(UssdMenuRequest ussdMenuRequest,String sessionId) {
        log.info("[{}] about to add menu with details : {}", sessionId , ussdMenuRequest.toString());
        UssdMenu menu = UssdMenu.builder()
                .response(ussdMenuRequest.getResponse())
                .options(ussdMenuRequest.getOptions())
                .menuKey(ussdMenuRequest.getMenuKey())
                .build();

        return ussdMenuDao.save(menu);
    }
}

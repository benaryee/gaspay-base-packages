package com.rancard.ussdapp.services;

import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.mongo.UssdMenu;
import com.rancard.ussdapp.model.request.UssdMenuRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UssdMenuService {

    private final UssdMenuDao ussdMenuDao;

    public UssdMenu getMenuByLevel(MenuLevel menuLevel, String sessionId) {
        log.info("[{} fetching menu with level : {} ]", sessionId, menuLevel);
        return ussdMenuDao.findByMenuLevel(menuLevel);
    }

    public UssdMenu addMenu(UssdMenuRequest ussdMenuRequest,String sessionId) {
        log.info("[{}] about to add menu with details : {}", sessionId , ussdMenuRequest.toString());
        UssdMenu menu = UssdMenu.builder()
                .response(ussdMenuRequest.getResponse())
                .options(ussdMenuRequest.getOptions())
                .menuLevel(ussdMenuRequest.getMenuLevel())
                .build();

        return ussdMenuDao.save(menu);
    }
}

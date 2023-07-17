package com.rancard.ussdapp.flow.actions.main;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.enums.SubMenuLevel;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.*;
import static com.rancard.ussdapp.model.enums.MenuKey.MAIN_MENU_RESPONSE;
import static com.rancard.ussdapp.model.enums.MenuLevel.MAIN;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class MainMenuActionRouter extends BotletActions {


    public MainMenuActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call() {

        switch (dispatchObject.getSession().getSubMenuLevel()) {

            case MAIN -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(MAIN_MENU_RESPONSE, dispatchObject, sessionId).replace("[name]" , user.getFirstname()));
                log.info("[{}] Main menu submenuLevel response : {}", sessionId, response);
                dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.MAIN_MENU_RESPONSE);
                dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.MAIN);
                return response;
            }


            case MAIN_MENU_RESPONSE -> {
                response.setContinueSession(false);
                response.setMessage(menuUtils.getResponse(UNDER_CONSTRUCTION_RESPONSE, dispatchObject, sessionId));
                log.info("[{}] Main menu submenuLevel response : {}", sessionId, response);
                dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.MAIN_MENU_RESPONSE);
                dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.MAIN);
                return response;
            }


        }

        response.setContinueSession(false);
        response.setMessage(menuUtils.getResponse(UNDER_CONSTRUCTION_RESPONSE, dispatchObject, sessionId));
        log.info("[{}] Main menu submenuLevel response : {}", sessionId, response);
        dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.MAIN_MENU_RESPONSE);
        dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.MAIN);
        return response;
    }
}
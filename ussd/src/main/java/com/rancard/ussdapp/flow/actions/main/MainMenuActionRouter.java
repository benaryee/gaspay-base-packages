package com.rancard.ussdapp.flow.actions.main;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.MAIN_MENU_RESPONSE;
import static com.rancard.ussdapp.model.enums.MenuLevel.MAIN;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class MainMenuActionRouter extends BotletActions {


    public MainMenuActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call(){
        response.setContinueSession(true);
        response.setMessage(menuUtils.getResponse(MAIN_MENU_RESPONSE,dispatchObject,sessionId).replace("[name]" , dispatchObject.getSession().getUser().getFirstname()));
        log.info("[{}] Main menu response : {}", sessionId , response);
        dispatchObject.getSession().setMenuLevel(MAIN);
        return response;

    }

}

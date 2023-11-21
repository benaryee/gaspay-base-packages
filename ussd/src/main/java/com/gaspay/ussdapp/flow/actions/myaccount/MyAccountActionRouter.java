package com.gaspay.ussdapp.flow.actions.myaccount;


import com.gaspay.ussdapp.flow.actions.BotletActions;
import com.gaspay.ussdapp.model.enums.SubMenuLevel;
import com.gaspay.ussdapp.model.response.UssdResponse;
import com.gaspay.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.gaspay.ussdapp.model.enums.MenuKey.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class MyAccountActionRouter extends BotletActions {


    public MyAccountActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call() {

        switch (dispatchObject.getSession().getSubMenuLevel()) {
            case MY_ACCOUNT_MAIN_MENU:

                switch(dispatchObject.getUssdRequest().getMessage()){
                    case "1" -> {
                        //Change Name
                    }
                    case "2" -> {
                        //Change Location
                    }
                    case "3" -> {
                        //Change Pin
                    }
                }

                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(MY_ACCOUNT_MAIN_MENU_RESPONSE, dispatchObject, sessionId));
                log.info("[{}] Main menu submenuLevel response : {}", sessionId, response);
                dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.MY_ACCOUNT_MAIN_MENU);
                dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.MAIN_MENU_RESPONSE);
                return response;

        }
        return null;
    }
}

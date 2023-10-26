package com.rancard.ussdapp.flow.actions.account.registration;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.flow.actions.main.MainMenuActionRouter;
import com.rancard.ussdapp.model.enums.MenuLevel;
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
import static com.rancard.ussdapp.model.enums.SubMenuLevel.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class RegistrationActionRouter extends BotletActions {


    public RegistrationActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call() {

        log.info("[{}] received request to route registration request : {} ", sessionId, dispatchObject.toString());

        //Handle Previous Action
        PreviousRegistrationActionResponseHandler previousActionResponseHandler = beanFactory.getBean(PreviousRegistrationActionResponseHandler.class);
        previousActionResponseHandler.setDispatchObject(dispatchObject);
        previousActionResponseHandler.setSessionId(sessionId);
        previousActionResponseHandler.call();

        if (dispatchObject.getSession().isThrowPreviousMenuError()){
            response.setMessage(menuUtils.getResponse(dispatchObject.getSession().getMenuKey(),dispatchObject,sessionId));
            response.setContinueSession(true);
            dispatchObject.getSession().setThrowPreviousMenuError(false);
            return response;
        }

        switch (dispatchObject.getSession().getSubMenuLevel()) {

            case REGISTRATION_FIRST_NAME -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(REGISTRATION_FIRST_NAME_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REGISTRATION_LAST_NAME);
                dispatchObject.getSession().setPreviousSubMenuLevel(REGISTRATION_FIRST_NAME);
                return response;
            }

            case REGISTRATION_LAST_NAME -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(REGISTRATION_LAST_NAME_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REGISTRATION_ADDRESS);
                dispatchObject.getSession().setPreviousSubMenuLevel(REGISTRATION_LAST_NAME);
                return response;
            }

            case REGISTRATION_ADDRESS -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(REGISTRATION_ADDRESS_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REGISTRATION_CURRENT_FUEL_SOURCE);
                dispatchObject.getSession().setPreviousSubMenuLevel(REGISTRATION_ADDRESS);
                return response;
            }

            case REGISTRATION_CURRENT_FUEL_SOURCE -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(REGISTRATION_CURRENT_FUEL_SOURCE_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REGISTRATION_FAMILY_SIZE);
                dispatchObject.getSession().setPreviousSubMenuLevel(REGISTRATION_CURRENT_FUEL_SOURCE);
                return response;
            }

            case REGISTRATION_FAMILY_SIZE -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(REGISTRATION_FAMILY_SIZE_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REGISTRATION_SET_PIN);
                dispatchObject.getSession().setPreviousSubMenuLevel(REGISTRATION_FAMILY_SIZE);
                return response;
            }

            case REGISTRATION_SET_PIN -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(REGISTRATION_SET_PIN_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REGISTRATION_CONFIRM_PIN);
                dispatchObject.getSession().setPreviousSubMenuLevel(REGISTRATION_SET_PIN);
                return response;
            }

            case REGISTRATION_CONFIRM_PIN -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(REGISTRATION_CONFIRM_PIN_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REGISTRATION_COMPLETE);
                dispatchObject.getSession().setPreviousSubMenuLevel(REGISTRATION_CONFIRM_PIN);
                return response;
            }

            case REGISTRATION_COMPLETE -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(REGISTRATION_COMPLETE_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REGISTRATION_COMPLETE_MAIN_MENU_OR_EXIT);
                dispatchObject.getSession().setPreviousSubMenuLevel(REGISTRATION_COMPLETE);
                return response;
            }

            case REGISTRATION_COMPLETE_MAIN_MENU_OR_EXIT -> {
               if(dispatchObject.getUssdRequest().getMessage().equals("2")){
                   log.info("[{}] user DOES NOT want to go to main menu ", sessionId);
                   response.setContinueSession(false);
                   response.setMessage(menuUtils.getResponse(REGISTRATION_COMPLETE_NO_FOLLOW_RESPONSE,dispatchObject,sessionId));
               }else{
                   response.setContinueSession(true);
                   response.setMessage(menuUtils.getResponse(MAIN_MENU_RESPONSE, dispatchObject, sessionId).replace("[name]", dispatchObject.getSession().getUser().getFirstname()));
                   log.info("[{}] Main menu submenuLevel response : {}", sessionId, response);
                   dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.MAIN_MENU_RESPONSE);
                   dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.MAIN);
                   dispatchObject.getSession().setMenuLevel(MenuLevel.MAIN);
               }
                return response;
            }

        }
        return null;
    }

}

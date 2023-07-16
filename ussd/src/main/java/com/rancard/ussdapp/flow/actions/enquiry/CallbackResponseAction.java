package com.rancard.ussdapp.flow.actions.enquiry;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.*;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.CALLBACK_PHONE_NUMBER_CONFIRMATION;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class CallbackResponseAction extends BotletActions {


    public CallbackResponseAction(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call(){

        switch (dispatchObject.getUssdRequest().getMessage()){
            case "1":
                log.info("[{}] user wants callback ", sessionId);
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(ENQUIRY_REQUEST_CALLBACK_MSISDN_CONFIRMATION_RESPONSE,dispatchObject,sessionId).replace("[msisdn]",dispatchObject.getUssdRequest().getMsisdn()));
                break;
            case "2":
                log.info("[{}] user DOES NOT want callback ", sessionId);
                response.setContinueSession(false);
                response.setMessage(menuUtils.getResponse(ENQUIRY_END_ENQUIRY_RESPONSE_NO_CALLBACK,dispatchObject,sessionId));
                break;
        }

        log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
        dispatchObject.getSession().setSubMenuLevel(CALLBACK_PHONE_NUMBER_CONFIRMATION);
        return response;

    }

}

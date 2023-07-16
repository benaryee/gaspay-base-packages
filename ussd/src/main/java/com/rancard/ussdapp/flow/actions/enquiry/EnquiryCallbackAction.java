package com.rancard.ussdapp.flow.actions.enquiry;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.enums.SubMenuLevel;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.ENQUIRY_REQUEST_CALLBACK_REQUEST_RESPONSE;


@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class EnquiryCallbackAction extends BotletActions {


    public EnquiryCallbackAction(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call(){
        response.setContinueSession(true);
        response.setMessage(menuUtils.getResponse(ENQUIRY_REQUEST_CALLBACK_REQUEST_RESPONSE,dispatchObject,sessionId));

        log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
        dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.REQUEST_CALLBACK_RESPONSE);
        return response;

    }

}

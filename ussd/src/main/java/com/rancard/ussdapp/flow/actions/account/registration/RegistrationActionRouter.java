package com.rancard.ussdapp.flow.actions.account.registration;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.flow.actions.enquiry.CallbackNumberConfirmationResponseAction;
import com.rancard.ussdapp.flow.actions.enquiry.CallbackResponseAction;
import com.rancard.ussdapp.flow.actions.enquiry.EnquiryCallbackAction;
import com.rancard.ussdapp.flow.actions.enquiry.MainEnquiryAction;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class RegistrationActionRouter extends BotletActions {


    public RegistrationActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call() {

        log.info("[{}] registration request router received request to route registration request : {} ", sessionId, dispatchObject.toString());
        switch (dispatchObject.getSession().getSubMenuLevel()) {

        }
        return null;
    }

}

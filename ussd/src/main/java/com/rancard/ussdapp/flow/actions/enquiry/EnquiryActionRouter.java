package com.rancard.ussdapp.flow.actions.enquiry;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.flow.actions.account.registration.PreviousRegistrationActionResponseHandler;
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
public class EnquiryActionRouter extends BotletActions {


    public EnquiryActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call() {

        PreviousEnquiryActionResponseHandler previousEnquiryActionResponseHandler = beanFactory.getBean(PreviousEnquiryActionResponseHandler.class);
        previousEnquiryActionResponseHandler.setDispatchObject(dispatchObject);
        previousEnquiryActionResponseHandler.setSessionId(sessionId);
        previousEnquiryActionResponseHandler.call();



        log.info("[{}] enquiry request router received request to route enquiry request : {} ", sessionId, dispatchObject.toString());
        switch (dispatchObject.getSession().getSubMenuLevel()) {
            case MAIN_ENQUIRY -> {
                MainEnquiryAction mainEnquiryAction = beanFactory.getBean(MainEnquiryAction.class);
                mainEnquiryAction.setDispatchObject(dispatchObject);
                mainEnquiryAction.setSessionId(sessionId);
                mainEnquiryAction.setResponse(response);
                return mainEnquiryAction.call();
            }

            case REQUEST_CALLBACK_QUESTION -> {
                EnquiryCallbackAction enquiryCallbackAction = beanFactory.getBean(EnquiryCallbackAction.class);
                enquiryCallbackAction.setDispatchObject(dispatchObject);
                enquiryCallbackAction.setSessionId(sessionId);
                enquiryCallbackAction.setResponse(response);
                return enquiryCallbackAction.call();
            }

            case REQUEST_CALLBACK_RESPONSE -> {
                CallbackResponseAction callbackResponseAction = beanFactory.getBean(CallbackResponseAction.class);
                callbackResponseAction.setDispatchObject(dispatchObject);
                callbackResponseAction.setSessionId(sessionId);
                callbackResponseAction.setResponse(response);
                return callbackResponseAction.call();
            }

            case CALLBACK_PHONE_NUMBER_CONFIRMATION -> {
                CallbackNumberConfirmationResponseAction callbackNumberConfirmationResponseAction = beanFactory.getBean(CallbackNumberConfirmationResponseAction.class);
                callbackNumberConfirmationResponseAction.setDispatchObject(dispatchObject);
                callbackNumberConfirmationResponseAction.setSessionId(sessionId);
                callbackNumberConfirmationResponseAction.setResponse(response);
                return callbackNumberConfirmationResponseAction.call();
            }

            case CALLBACK_PHONE_NUMBER_ENTRY -> {
                CallbackNumberEntryAction callbackNumberEntryAction = beanFactory.getBean(CallbackNumberEntryAction.class);
                callbackNumberEntryAction.setDispatchObject(dispatchObject);
                callbackNumberEntryAction.setSessionId(sessionId);
                callbackNumberEntryAction.setResponse(response);
                return callbackNumberEntryAction.call();
            }


        }
        return null;
    }

}

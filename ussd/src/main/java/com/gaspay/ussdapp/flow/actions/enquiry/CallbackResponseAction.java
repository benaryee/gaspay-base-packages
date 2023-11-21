package com.gaspay.ussdapp.flow.actions.enquiry;


import com.gaspay.ussdapp.flow.actions.BotletActions;
import com.gaspay.ussdapp.model.response.UssdResponse;
import com.gaspay.ussdapp.services.EnquiryService;
import com.gaspay.ussdapp.utils.MailUtils;
import com.gaspay.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.gaspay.ussdapp.model.enums.MenuKey.*;
import static com.gaspay.ussdapp.model.enums.SubMenuLevel.CALLBACK_PHONE_NUMBER_CONFIRMATION;
import static com.gaspay.ussdapp.model.enums.SubMenuLevel.REQUEST_CALLBACK_RESPONSE;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class CallbackResponseAction extends BotletActions {

    protected final EnquiryService enquiryService;

    public CallbackResponseAction(BeanFactory beanFactory, MenuUtils menuUtils, EnquiryService enquiryService) {
        super(beanFactory, menuUtils);
        this.enquiryService = enquiryService;
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
                enquiryService.makeEnquiry(dispatchObject.getSession().getEnquiry(), sessionId);
                String htmlContent = PreviousEnquiryActionResponseHandler.getNewEnquiryEmailTemplate()
                        .replace("{{msisdn}}", dispatchObject.getSession().getEnquiry().getAlternativeNumber() != null ? dispatchObject.getSession().getEnquiry().getAlternativeNumber() : dispatchObject.getUssdRequest().getMsisdn())
                        .replace("{{message}}", dispatchObject.getSession().getEnquiry().getRequest())
                        .replace("{{callback}}", dispatchObject.getSession().getEnquiry().isCallback() ? "true" : "false");
                MailUtils.sendNotification("bernard.aryee@rancard.com", "New Enquiry", htmlContent);
                break;
        }

        log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
        dispatchObject.getSession().setSubMenuLevel(CALLBACK_PHONE_NUMBER_CONFIRMATION);
        dispatchObject.getSession().setPreviousSubMenuLevel(REQUEST_CALLBACK_RESPONSE);
        return response;

    }

}

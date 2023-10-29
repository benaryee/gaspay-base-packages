package com.rancard.ussdapp.flow.actions.enquiry;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.services.EnquiryService;
import com.rancard.ussdapp.utils.MailUtils;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.*;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.CALLBACK_PHONE_NUMBER_CONFIRMATION;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.CALLBACK_PHONE_NUMBER_ENTRY;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class CallbackNumberConfirmationResponseAction extends BotletActions {

    protected final EnquiryService enquiryService;

    public CallbackNumberConfirmationResponseAction(BeanFactory beanFactory, MenuUtils menuUtils, EnquiryService enquiryService) {
        super(beanFactory, menuUtils);
        this.enquiryService = enquiryService;
    }

    public UssdResponse call(){

        switch (dispatchObject.getUssdRequest().getMessage()) {
            case "1" -> {
                log.info("[{}] current number is user's preferred callback number ", sessionId);
                response.setContinueSession(false);
                response.setMessage(menuUtils.getResponse(ENQUIRY_END_ENQUIRY_RESPONSE_CALLBACK, dispatchObject, sessionId).replace("[msisdn]", dispatchObject.getUssdRequest().getMsisdn()));
                enquiryService.makeEnquiry(dispatchObject.getSession().getEnquiry(), sessionId);
                String htmlContent = PreviousEnquiryActionResponseHandler.getNewEnquiryEmailTemplate()
                        .replace("{{msisdn}}", dispatchObject.getSession().getEnquiry().getAlternativeNumber() != null ? dispatchObject.getSession().getEnquiry().getAlternativeNumber() : dispatchObject.getUssdRequest().getMsisdn())
                        .replace("{{message}}", dispatchObject.getSession().getEnquiry().getRequest())
                        .replace("{{callback}}", dispatchObject.getSession().getEnquiry().isCallback() ? "true" : "false");
                MailUtils.sendNotification("bernard.aryee@rancard.com", "New Enquiry", htmlContent);
            }
            case "2" -> {
                log.info("[{}] user wants different number to be called ", sessionId);
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(ENQUIRY_ALTERNATIVE_CALLBACK_NUMBER_RESPONSE, dispatchObject, sessionId));
                dispatchObject.getSession().setSubMenuLevel(CALLBACK_PHONE_NUMBER_ENTRY);
                dispatchObject.getSession().setPreviousSubMenuLevel(CALLBACK_PHONE_NUMBER_CONFIRMATION);
            }
        }

        log.info("[{}] Main enquiry submenuLevel response : {}", sessionId , response);
        return response;

    }

}

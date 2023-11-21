package com.gaspay.ussdapp.flow.actions.enquiry;


import com.gaspay.ussdapp.flow.actions.BotletActions;
import com.gaspay.ussdapp.model.enums.SubMenuLevel;
import com.gaspay.ussdapp.model.response.UssdResponse;
import com.gaspay.ussdapp.utils.MenuUtils;
import com.gaspay.ussdapp.utils.MsisdnUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.gaspay.ussdapp.model.enums.MenuKey.*;


@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class CallbackNumberEntryAction extends BotletActions {


    public CallbackNumberEntryAction(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call(){

        if(MsisdnUtils.isValidPhoneNumber(dispatchObject.getUssdRequest().getMsisdn())){
            response.setContinueSession(false);
            response.setMessage(menuUtils.getResponse(ENQUIRY_END_ENQUIRY_RESPONSE_CALLBACK,dispatchObject,sessionId));
            log.info("[{}] User entered valid number for callback : {}", sessionId , response);
            dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.REQUEST_CALLBACK_RESPONSE);
            dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.CALLBACK_PHONE_NUMBER_ENTRY);


            PreviousEnquiryActionResponseHandler previousEnquiryActionResponseHandler = beanFactory.getBean(PreviousEnquiryActionResponseHandler.class);
            previousEnquiryActionResponseHandler.setDispatchObject(dispatchObject);
            previousEnquiryActionResponseHandler.setSessionId(sessionId);
            previousEnquiryActionResponseHandler.call();


            return response;
        }else{
            response.setContinueSession(true);
            response.setMessage(menuUtils.getResponse(ENQUIRY_ALTERNATIVE_CALLBACK_NUMBER_RESPONSE,dispatchObject,sessionId));
            response.setMessage("Invalid Phone Number entered\n"+response.getMessage());
            log.info("[{}] User entered invalid number for callback : {}", sessionId , response);
            dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.CALLBACK_PHONE_NUMBER_ENTRY);
            dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.CALLBACK_PHONE_NUMBER_ENTRY);
            return response;
        }
    }

}

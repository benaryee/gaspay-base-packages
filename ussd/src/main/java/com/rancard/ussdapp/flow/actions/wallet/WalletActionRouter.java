package com.rancard.ussdapp.flow.actions.wallet;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.flow.actions.enquiry.*;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.*;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class WalletActionRouter extends BotletActions {


    public WalletActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call() {

        PreviousWalletActionResponseHandler previousEnquiryActionResponseHandler = beanFactory.getBean(PreviousWalletActionResponseHandler.class);
        previousEnquiryActionResponseHandler.setDispatchObject(dispatchObject);
        previousEnquiryActionResponseHandler.setSessionId(sessionId);
        previousEnquiryActionResponseHandler.call();



        log.info("[{}] enquiry request router received request to route enquiry request : {} ", sessionId, dispatchObject.toString());
        switch (dispatchObject.getSession().getSubMenuLevel()) {
            case WALLET_MAIN_MENU -> {

            }

            case TOPUP_AMOUNT -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(TOPUP_AMOUNT_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Topup amount submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(TOPUP_AMOUNT_CONFIRMATION);
                dispatchObject.getSession().setPreviousSubMenuLevel(TOPUP_AMOUNT);
                return response;
            }

            case TOPUP_AMOUNT_CONFIRMATION -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(TOPUP_AMOUNT_CONFIRMATION_RESPONSE,dispatchObject,sessionId).replace("[amount]",dispatchObject.getSession().getTopUpAmount()));
                log.info("[{}] Topup amount confirmation submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(TOPUP_AWAITING_APPROVAL);
                dispatchObject.getSession().setPreviousSubMenuLevel(TOPUP_AMOUNT_CONFIRMATION);
                return response;
            }

            case TOPUP_AWAITING_APPROVAL -> {
                response.setContinueSession(false);
                response.setMessage(menuUtils.getResponse(TOPUP_AWAITING_APPROVAL_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Awaiting approval submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(TOPUP_AMOUNT_CONFIRMATION);
                dispatchObject.getSession().setPreviousSubMenuLevel(TOPUP_AMOUNT_CONFIRMATION);
                return response;
            }

        }
        return null;
    }

}

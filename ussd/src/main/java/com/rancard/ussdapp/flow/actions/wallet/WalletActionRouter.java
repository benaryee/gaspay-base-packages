package com.rancard.ussdapp.flow.actions.wallet;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.dto.WalletResponseDto;
import com.rancard.ussdapp.model.enums.SubMenuLevel;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.services.PaymentService;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.*;
import static com.rancard.ussdapp.model.enums.MenuKey.TOPUP_AMOUNT_CONFIRMATION_RESPONSE;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class WalletActionRouter extends BotletActions {


    private final PaymentService paymentService;
    public WalletActionRouter(BeanFactory beanFactory, MenuUtils menuUtils, PaymentService paymentService) {
        super(beanFactory, menuUtils);
        this.paymentService = paymentService;
    }

    public UssdResponse call() {

        PreviousWalletActionResponseHandler previousEnquiryActionResponseHandler = beanFactory.getBean(PreviousWalletActionResponseHandler.class);
        previousEnquiryActionResponseHandler.setDispatchObject(dispatchObject);
        previousEnquiryActionResponseHandler.setSessionId(sessionId);
        previousEnquiryActionResponseHandler.call();


        log.info("[{}] enquiry request router received request to route enquiry request : {} ", sessionId, dispatchObject.toString());
        switch (dispatchObject.getSession().getSubMenuLevel()) {
            case WALLET_MAIN_MENU -> {
               switch (dispatchObject.getUssdRequest().getMessage()){
                   case "1" ->{
                       response.setContinueSession(false);
                       response.setMessage("Your current Gaspay Wallet balance is GHS " + getBalanceString(dispatchObject.getSession().getUser().getWalletId()));
                       log.info("[{}] Wallet balance submenuLevel response : {}", sessionId , response);
                       dispatchObject.getSession().setSubMenuLevel(WALLET_MAIN_MENU);
                       dispatchObject.getSession().setPreviousSubMenuLevel(WALLET_MAIN_MENU);
                       return response;

                   }
                   default -> {
                       response.setContinueSession(false);
                       response.setMessage("The select option is not available yet. Please try again later");
                       log.info("[{}] Main menu submenuLevel response : {}", sessionId, response);
                       dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.MAIN_MENU_RESPONSE);
                       dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.MAIN);
                       return response;

                   }
               }
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
                dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.TOPUP_AMOUNT_CONFIRMATION_RESPONSE);
                dispatchObject.getSession().setPreviousSubMenuLevel(TOPUP_AMOUNT_CONFIRMATION);
                return response;
            }

            case TOPUP_AMOUNT_CONFIRMATION_RESPONSE -> {
                if(dispatchObject.getUssdRequest().getMessage().equals("1")){
                    response.setContinueSession(false);
                    response.setMessage(menuUtils.getResponse(TOPUP_AWAITING_APPROVAL_RESPONSE,dispatchObject,sessionId));
                    log.info("[{}] Awaiting approval submenuLevel response : {}", sessionId , response);
                    dispatchObject.getSession().setSubMenuLevel(TOPUP_AMOUNT_CONFIRMATION);
                    dispatchObject.getSession().setPreviousSubMenuLevel(TOPUP_AMOUNT_CONFIRMATION);
                }else{
                    response.setContinueSession(true);
                    response.setMessage(menuUtils.getResponse(TOPUP_AMOUNT_RESPONSE,dispatchObject,sessionId));
                    log.info("[{}] Topup amount submenuLevel response : {}", sessionId , response);
                    dispatchObject.getSession().setSubMenuLevel(TOPUP_AMOUNT_CONFIRMATION);
                    dispatchObject.getSession().setPreviousSubMenuLevel(TOPUP_AMOUNT);
                }
                return response;
            }

        }
        return null;
    }

    private String getBalanceString(String walletId) {
        log.info("[{}] getting balance for walletId : {}", sessionId, walletId);
        WalletResponseDto walletResponseDto = paymentService.getBalance(walletId,sessionId);
        if (walletResponseDto == null){
            log.info("[{}] wallet with id {} not found", sessionId, walletId);
            return "0.00";
        }else {
            dispatchObject.getSession().setWallet(walletResponseDto);
            return String.valueOf(walletResponseDto.getBalance());
        }
    }
}


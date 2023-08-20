package com.rancard.ussdapp.flow.actions.purchase.refillcylinder;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.dto.OrderDto;
import com.rancard.ussdapp.model.dto.WalletResponseDto;
import com.rancard.ussdapp.model.enums.SubMenuLevel;
import com.rancard.ussdapp.model.response.ApiResponse;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.services.OrderService;
import com.rancard.ussdapp.services.PaymentService;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.*;
import static com.rancard.ussdapp.model.enums.MenuKey.WALLET_PASSWORD_RESPONSE;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class RefillCylinderActionRouter extends BotletActions {

    private final PaymentService paymentService;
    private final OrderService orderService;

    public RefillCylinderActionRouter(BeanFactory beanFactory, MenuUtils menuUtils, PaymentService paymentService, OrderService orderService) {
        super(beanFactory, menuUtils);
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    public UssdResponse call() {

        PreviousRefillActionResponseHandler previousRefillActionResponseHandler = beanFactory.getBean(PreviousRefillActionResponseHandler.class);
        previousRefillActionResponseHandler.setDispatchObject(dispatchObject);
        previousRefillActionResponseHandler.setSessionId(sessionId);
        previousRefillActionResponseHandler.call();

        if (dispatchObject.getSession().isThrowPreviousMenuError()){
            response.setMessage(menuUtils.getResponse(dispatchObject.getSession().getMenuKey(),dispatchObject,sessionId));
            response.setContinueSession(true);
            dispatchObject.getSession().setThrowPreviousMenuError(false);
            return response;
        }

        log.info("[{}] enquiry request router received request to route enquiry request : {} ", sessionId, dispatchObject.toString());
        switch (dispatchObject.getSession().getSubMenuLevel()) {

            case REFILL_MAIN_MENU -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(PURCHASE_SIZE_OPTIONS_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Purchase main menu submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REFILL_DIGITAL_ADDRESS);
                dispatchObject.getSession().setPreviousSubMenuLevel(REFILL_MAIN_MENU);
                return response;
            }
            case REFILL_DIGITAL_ADDRESS ->{
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(PURCHASE_DIGITAL_ADDRESS_CONFIRM_OR_ENTER,dispatchObject,sessionId)
                        .replace("[ghanaPostGps]",dispatchObject.getSession().getUser().getAddress().getGhanaPostGps())
                                .replace("[city]",dispatchObject.getSession().getUser().getAddress().getCity())
                                        .replace("[street]",dispatchObject.getSession().getUser().getAddress().getStreet()));
                log.info("[{}] Purchase main menu submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REFILL_CONFIRM);
                dispatchObject.getSession().setPreviousSubMenuLevel(REFILL_DIGITAL_ADDRESS);
                return response;
            }

            case REFILL_CONFIRM -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(PURCHASE_ORDER_CONFIRMATION_RESPONSE,dispatchObject,sessionId)
                        .replace("[size]",dispatchObject.getSession().getOrderDto().getOrderItemsDtoList().get(0).getSize())
                        .replace("[price]","80")
                        .replace("[delivery]","10")
                        .replace("[total]","90")
                );
                log.info("[{}] Purchase main menu submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(REFILL_PAYMENT_WALLET);
                dispatchObject.getSession().setPreviousSubMenuLevel(REFILL_CONFIRM);
                return response;
            }

            case REFILL_PAYMENT_WALLET -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(PURCHASE_PAYMENT_METHOD,dispatchObject,sessionId)
                        .replace("[balance]", getBalanceString(dispatchObject.getSession().getUser().getWalletId()) )
                );
                log.info("[{}] Purchase main menu submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.REFILL_PAYMENT_WALLET_RESPONSE);
                dispatchObject.getSession().setPreviousSubMenuLevel(REFILL_PAYMENT_WALLET);
                return response;
            }
            
            case REFILL_PAYMENT_WALLET_RESPONSE -> {
                if(dispatchObject.getUssdRequest().getMessage().equalsIgnoreCase("1")) {
                    response.setContinueSession(true);
                    response.setMessage(menuUtils.getResponse(WALLET_PASSWORD_RESPONSE, dispatchObject, sessionId)
                            .replace("[balance]", getBalanceString(dispatchObject.getSession().getUser().getWalletId()))
                    );
                    log.info("[{}] Purchase main menu submenuLevel response : {}", sessionId, response);
                    dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.WALLET_PASSWORD_RESPONSE);
                    dispatchObject.getSession().setPreviousSubMenuLevel(REFILL_PAYMENT_WALLET_RESPONSE);
                    return response;
                }else if(dispatchObject.getUssdRequest().getMessage().equalsIgnoreCase("2")) {
                    response.setContinueSession(false);
                    response.setMessage(menuUtils.getResponse(MOMO_CHECK_AND_CONFIRM, dispatchObject, sessionId));

                    log.info("[{}] Purchase main menu submenuLevel response : {}", sessionId, response);
                    return response;
                }
            }

            case WALLET_PASSWORD_RESPONSE -> {
                if(dispatchObject.getUssdRequest().getMessage().equals(dispatchObject.getSession().getWallet().getWalletKey())){
                    paymentService.deductFromWallet(dispatchObject.getSession().getUser().getWalletId(),
                            dispatchObject.getSession().getOrderDto().getTotalAmount(),sessionId);
                    //Deduct from wallet

                    ApiResponse<OrderDto> orderDtoApiResponse = orderService.placeOrder(dispatchObject.getSession().getOrderDto(),sessionId);

                    if(orderDtoApiResponse.getCode() != 200){
                        paymentService.creditWallet(dispatchObject.getSession().getUser().getWalletId(),
                                dispatchObject.getSession().getOrderDto().getTotalAmount(),sessionId);
                    }else{

                    }
                    //Place order
                    //If order fails, refund wallet
                    //If order succeeds, send sms to user
                    //Send sms to admin
                    //Send sms to delivery agent
                    //Send sms to vendor

                    //Place order
                }else{

                }
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

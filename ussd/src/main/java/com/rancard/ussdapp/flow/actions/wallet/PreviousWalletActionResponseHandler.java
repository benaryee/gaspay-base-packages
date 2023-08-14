package com.rancard.ussdapp.flow.actions.wallet;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.dto.TopupRequestDto;
import com.rancard.ussdapp.model.enums.Channel;
import com.rancard.ussdapp.services.PaymentService;
import com.rancard.ussdapp.services.UserService;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class PreviousWalletActionResponseHandler extends BotletActions {

    private final PaymentService paymentService;


    public PreviousWalletActionResponseHandler(BeanFactory beanFactory, MenuUtils menuUtils, PaymentService paymentService) {
        super(beanFactory, menuUtils);

        this.paymentService = paymentService;
    }

    public void call() {

        log.info("[{}] registration request router received request to route registration request : {} ", sessionId, dispatchObject.toString());

        if(dispatchObject.getSession().getPreviousSubMenuLevel() == null)
            return;

        switch(dispatchObject.getSession().getPreviousSubMenuLevel()) {

            case TOPUP_AMOUNT -> {
                if(!dispatchObject.getUssdRequest().getMessage().matches("\\d+")){
                    dispatchObject.getSession().setThrowPreviousMenuError(true);
                    dispatchObject.getSession().setMenuKey(TOPUP_AMOUNT_INVALID);
                    return;
                }
                dispatchObject.getSession().setTopUpAmount(dispatchObject.getUssdRequest().getMessage());
            }

            case TOPUP_AMOUNT_CONFIRMATION -> {
                if(dispatchObject.getUssdRequest().getMessage().equals("1")){

                    dispatchObject.getSession().getUser().setPhone(dispatchObject.getUssdRequest().getMsisdn());
                    TopupRequestDto topupRequestDto = TopupRequestDto.builder()
                            .amount(dispatchObject.getSession().getTopUpAmount())
                            .user(dispatchObject.getSession().getUser())
                            .sessionId(sessionId)
                            .mobileNetwork(dispatchObject.getUssdRequest().getMobileNetwork())
                            .channel(Channel.USSD)
                            .build();

                    log.info("Sending topup request to user");
                    paymentService.sendPaymentRequestToUser(topupRequestDto, sessionId);
                }

            }
        }
    }

    private String getNewAccountEmailTemplate(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>New Account Created</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>New Account Created</h1>\n" +
                "    <p>Great news! We have a new user!</p>\n" +
                "    <p>Here are the details of the newly created account:</p>\n" +
                "    <ul>\n" +
                "        <li><strong>Phone Number:</strong> {{msisdn}}</li>\n" +
                "        <li><strong>Name:</strong> {{firstname}} {{lastname}}</li>\n" +
                "    </ul>\n" +
                "    <p>Let's celebrate this achievement together!</p>\n" +
                "    <p>Thank you for your continuous support.</p>\n" +
                "    <p>Best regards,</p>\n" +
                "    <p>Your Application Team</p>\n" +
                "</body>\n" +
                "</html>\n";
    }

}

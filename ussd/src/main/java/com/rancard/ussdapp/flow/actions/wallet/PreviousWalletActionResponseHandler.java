package com.rancard.ussdapp.flow.actions.wallet;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.dto.UserDto;
import com.rancard.ussdapp.model.payload.Address;
import com.rancard.ussdapp.services.UserService;
import com.rancard.ussdapp.utils.MailUtils;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class PreviousWalletActionResponseHandler extends BotletActions {

    protected final UserService userService;


    public PreviousWalletActionResponseHandler(BeanFactory beanFactory, MenuUtils menuUtils, UserService userService) {
        super(beanFactory, menuUtils);
        this.userService = userService;
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

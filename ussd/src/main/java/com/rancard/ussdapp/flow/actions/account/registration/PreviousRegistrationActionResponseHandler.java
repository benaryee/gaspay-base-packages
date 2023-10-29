package com.rancard.ussdapp.flow.actions.account.registration;


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
public class PreviousRegistrationActionResponseHandler extends BotletActions {

    protected final UserService userService;


    public PreviousRegistrationActionResponseHandler(BeanFactory beanFactory, MenuUtils menuUtils, UserService userService) {
        super(beanFactory, menuUtils);
        this.userService = userService;
    }

    public void call() {

        log.info("[{}] registration request router received request to route registration request : {} ", sessionId, dispatchObject.toString());

        if(dispatchObject.getSession().getPreviousSubMenuLevel() == null)
            return;

        switch(dispatchObject.getSession().getPreviousSubMenuLevel()) {

            case REGISTRATION_FIRST_NAME -> {
                dispatchObject.getSession().setUser(new UserDto());
                dispatchObject.getSession().getUser().setPhone(dispatchObject.getUssdRequest().getMsisdn());
                dispatchObject.getSession().getUser().setFirstname(dispatchObject.getUssdRequest().getMessage());
            }

            case REGISTRATION_LAST_NAME -> {

                dispatchObject.getSession().getUser().setLastname(dispatchObject.getUssdRequest().getMessage());
            }

            case REGISTRATION_ADDRESS -> {
                Address address = userService.findAddressByGps(dispatchObject.getUssdRequest().getMessage(), sessionId);

                if(address != null && !StringUtils.isAnyBlank(address.getLatitude() , address.getLongitude())){
                    dispatchObject.getSession().getUser().setAddress(address);
                }else{
                    dispatchObject.getSession().setThrowPreviousMenuError(true);
                    dispatchObject.getSession().setMenuKey(REGISTRATION_ADDRESS_INVALID);
                }
            }


            case REGISTRATION_CURRENT_FUEL_SOURCE -> {
                dispatchObject.getSession().getUser().setCurrentFuelSource((String) dispatchObject.getSession().getOptions()
                        .get(Integer.parseInt(dispatchObject.getUssdRequest().getMessage())));
            }

            case REGISTRATION_FAMILY_SIZE -> {
                dispatchObject.getSession().getUser().setFamilySize((String) dispatchObject.getSession().getOptions()
                        .get(Integer.parseInt(dispatchObject.getUssdRequest().getMessage())));

            }

            case REGISTRATION_SET_PIN -> {
                if(dispatchObject.getUssdRequest().getMessage().matches("\\d{4}") && dispatchObject.getUssdRequest().getMessage().length() == 4){
                    dispatchObject.getSession().getUser().setPassword(dispatchObject.getUssdRequest().getMessage());
                }else{
                    dispatchObject.getSession().setThrowPreviousMenuError(true);
                    dispatchObject.getSession().setMenuKey(REGISTRATION_PIN_INVALID);
                }
            }

            case REGISTRATION_CONFIRM_PIN -> {
                if(!dispatchObject.getUssdRequest().getMessage().matches(dispatchObject.getSession().getUser().getPassword())){
                    dispatchObject.getSession().setThrowPreviousMenuError(true);
                    dispatchObject.getSession().setMenuKey(REGISTRATION_CONFIRM_PIN_INVALID);
                }
                dispatchObject.getSession().getUser().setConfirmPassword(dispatchObject.getUssdRequest().getMessage());
                user = userService.registerUser(dispatchObject.getSession().getUser(), sessionId);


                String htmlContent = getNewAccountEmailTemplate()
                        .replace("{{msisdn}}", user.getPhone())
                        .replace("{{firstname}}", user.getFirstname())
                        .replace("{{lastname}}", user.getLastname());

                MailUtils.sendNotification("bernard.aryee@rancard.com", "New Registration", htmlContent);
            }

            case REGISTRATION_COMPLETE -> {

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

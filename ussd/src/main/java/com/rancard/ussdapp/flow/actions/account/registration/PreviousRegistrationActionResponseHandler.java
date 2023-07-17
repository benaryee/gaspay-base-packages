package com.rancard.ussdapp.flow.actions.account.registration;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.mongo.User;
import com.rancard.ussdapp.services.UserService;
import com.rancard.ussdapp.utils.MailUtils;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
                dispatchObject.getSession().setUser(new User());
                dispatchObject.getSession().getUser().setMsisdn(dispatchObject.getUssdRequest().getMsisdn());
                dispatchObject.getSession().getUser().setFirstname(dispatchObject.getUssdRequest().getMessage());
                break;
            }

            case REGISTRATION_LAST_NAME -> {
                dispatchObject.getSession().getUser().setLastname(dispatchObject.getUssdRequest().getMessage());
                break;
            }

            case REGISTRATION_ADDRESS -> {
                dispatchObject.getSession().getUser().getAddress().setLocation(dispatchObject.getUssdRequest().getMessage());
                break;
            }


            case REGISTRATION_CURRENT_FUEL_SOURCE -> {
                dispatchObject.getSession().getUser().setCurrentFuelSource(dispatchObject.getSession().getPreviousOptions()
                        .get(Integer.parseInt(dispatchObject.getUssdRequest().getMessage())));
                break;
            }

            case REGISTRATION_FAMILY_SIZE -> {
                dispatchObject.getSession().getUser().setCurrentFuelSource(dispatchObject.getSession().getPreviousOptions()
                        .get(Integer.parseInt(dispatchObject.getUssdRequest().getMessage())));
                user = userService.registerUser(dispatchObject.getSession().getUser(), sessionId);



                String htmlContent = getNewAccountEmailTemplate()
                        .replace("{{msisdn}}", user.getMsisdn())
                        .replace("{{firstname}}", user.getFirstname())
                        .replace("{{lastname}}", user.getLastname())
                        .replace("{{totalAccounts}}", String.valueOf(userService.getTotalNumberOfUsers(true)));

                MailUtils.sendNotification("bernard.aryee@rancard.com", "New Registration", htmlContent);
                break;
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
                "    <p>We now have a total of {{totalAccounts}} accounts registered on the platform.</p>\n" +
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

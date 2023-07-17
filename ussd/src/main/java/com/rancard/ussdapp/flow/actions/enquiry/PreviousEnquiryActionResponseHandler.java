package com.rancard.ussdapp.flow.actions.enquiry;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.mongo.Enquiry;
import com.rancard.ussdapp.services.EnquiryService;
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
public class PreviousEnquiryActionResponseHandler extends BotletActions {

    protected final EnquiryService enquiryService;


    public PreviousEnquiryActionResponseHandler(BeanFactory beanFactory, MenuUtils menuUtils,EnquiryService enquiryService) {
        super(beanFactory, menuUtils);
        this.enquiryService = enquiryService;
    }

    public void call() {

        log.info("[{}] registration request router received request to route registration request : {} ", sessionId, dispatchObject.toString());

        if(dispatchObject.getSession().getPreviousSubMenuLevel() == null)
            return;

        switch(dispatchObject.getSession().getPreviousSubMenuLevel()) {

            case MAIN_ENQUIRY -> {
                dispatchObject.getSession().setEnquiry(new Enquiry());
                dispatchObject.getSession().getEnquiry().setMsisdn(dispatchObject.getUssdRequest().getMsisdn());
                dispatchObject.getSession().getEnquiry().setRequest(dispatchObject.getUssdRequest().getMessage());
                break;
            }

            case REQUEST_CALLBACK_QUESTION -> {
                if(Integer.parseInt(dispatchObject.getUssdRequest().getMessage()) == 1){
                    dispatchObject.getSession().getEnquiry().setCallback(true);
                }else{
                    dispatchObject.getSession().getEnquiry().setCallback(false);
                }
                break;
            }

            case CALLBACK_PHONE_NUMBER_ENTRY -> {
                dispatchObject.getSession().getEnquiry().setAlternativeNumber(dispatchObject.getUssdRequest().getMessage());
                enquiryService.makeEnquiry(dispatchObject.getSession().getEnquiry(), sessionId);


                String htmlContent = getNewEnquiryEmailTemplate()
                        .replace("{{msisdn}}", dispatchObject.getSession().getEnquiry().getAlternativeNumber() != null ? dispatchObject.getSession().getEnquiry().getAlternativeNumber() : dispatchObject.getUssdRequest().getMsisdn())
                        .replace("{{message}}", dispatchObject.getSession().getEnquiry().getRequest())
                        .replace("{{callback}}", dispatchObject.getSession().getEnquiry().isCallback() ? "Yes" : "No");

                MailUtils.sendNotification("bernard.aryee@rancard.com", "New Enquiry", htmlContent);
                break;
            }


        }
    }

    protected static String getNewEnquiryEmailTemplate(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>New Enquiry</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>New Enquiry Received</h1>\n" +
                "    <p>A new enquiry has been submitted by a user.</p>\n" +
                "    <p>Here are the details:</p>\n" +
                "    <ul>\n" +
                "        <li><strong>Message:</strong> {{message}}</li>\n" +
                "        <li><strong>Callback Requested: </strong> {{callback}}</li>\n" +
                "        <li><strong>Phone Number:</strong> {{msisdn}}</li>\n" +
                "    </ul>\n" +
                "    <p>Please review and respond to the enquiry promptly.</p>\n" +
                "    <p>Thank you.</p>\n" +
                "    <p>Best regards,</p>\n" +
                "    <p>Your Application Team</p>\n" +
                "</body>\n" +
                "</html>";
    }

}

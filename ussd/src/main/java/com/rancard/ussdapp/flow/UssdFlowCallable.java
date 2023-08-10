package com.rancard.ussdapp.flow;


import com.rancard.ussdapp.flow.actions.InvalidOptionAction;
import com.rancard.ussdapp.flow.actions.main.MainMenuActionRouter;
import com.rancard.ussdapp.flow.actions.account.registration.RegistrationActionRouter;
import com.rancard.ussdapp.flow.actions.enquiry.MainEnquiryAction;
import com.rancard.ussdapp.flow.actions.enquiry.EnquiryActionRouter;
import com.rancard.ussdapp.flow.actions.wallet.WalletActionRouter;
import com.rancard.ussdapp.model.payload.DispatchObject;
import com.rancard.ussdapp.model.response.UssdResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

import static com.rancard.ussdapp.model.enums.MenuLevel.ENQUIRY;
import static com.rancard.ussdapp.model.enums.MenuLevel.REGISTRATION;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.REGISTRATION_FIRST_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class UssdFlowCallable implements Callable<UssdResponse> {

    private String sessionId;

    private HttpServletRequest httpServletRequest;
    private UssdResponse response = new UssdResponse();
    private DispatchObject dispatchObject;

    private final BeanFactory beanFactory;


    public UssdResponse execute(DispatchObject dispatchObject, HttpServletRequest httpServletRequest , UssdResponse response, String sessionId) throws InterruptedException {
        this.sessionId = sessionId;
        this.httpServletRequest = httpServletRequest;
        this.dispatchObject = dispatchObject;
        this.response = response;
        return call();
    }


    public UssdResponse call() throws InterruptedException {

        log.info("[{}] about to use ussd callable to process request ::: Current Dispatch Object : {}", sessionId, dispatchObject);

        if(!dispatchObject.getSession().isInitialRequest() && dispatchObject.getSession().getOptions() != null &&
                dispatchObject.getSession().getOptions().size() > 0 &&
                dispatchObject.getSession().getOptions().size() < Integer.parseInt(dispatchObject.getUssdRequest().getMessage())){

            InvalidOptionAction invalidOptionAction = beanFactory.getBean(InvalidOptionAction.class);
            invalidOptionAction.setDispatchObject(dispatchObject);
            invalidOptionAction.setSessionId(sessionId);
            invalidOptionAction.setResponse(response);
            return invalidOptionAction.call();

        }


        switch (dispatchObject.getSession().getMenuLevel()){
            case UNREGISTERED_USER_INITIAL_REQUEST -> {
                switch (dispatchObject.getUssdRequest().getMessage()){
                    case "1"->{
                        log.info("[{}] user has opted for for account creation process", sessionId);
                        dispatchObject.getSession().setMenuLevel(REGISTRATION);
                        dispatchObject.getSession().setSubMenuLevel(REGISTRATION_FIRST_NAME);
                        RegistrationActionRouter registrationActionRouter = beanFactory.getBean(RegistrationActionRouter.class);
                        registrationActionRouter.setDispatchObject(dispatchObject);
                        registrationActionRouter.setSessionId(sessionId);
                        registrationActionRouter.setResponse(response);
                        return registrationActionRouter.call();
                    }
                    case "2"->{
                        log.info("[{}] user has opted to make enquiry" , sessionId);
                        dispatchObject.getSession().setMenuLevel(ENQUIRY);
                        MainEnquiryAction mainEnquiryAction = beanFactory.getBean(MainEnquiryAction.class);
                        mainEnquiryAction.setDispatchObject(dispatchObject);
                        mainEnquiryAction.setSessionId(sessionId);
                        mainEnquiryAction.setResponse(response);
                        return mainEnquiryAction.call();
                    }

                }
            }


            case ENQUIRY -> {
                EnquiryActionRouter enquiryActionRouter = beanFactory.getBean(EnquiryActionRouter.class);
                enquiryActionRouter.setDispatchObject(dispatchObject);
                enquiryActionRouter.setSessionId(sessionId);
                enquiryActionRouter.setResponse(response);
                return enquiryActionRouter.call();
            }

            case REGISTRATION -> {
                RegistrationActionRouter registrationActionRouter = beanFactory.getBean(RegistrationActionRouter.class);
                registrationActionRouter.setDispatchObject(dispatchObject);
                registrationActionRouter.setSessionId(sessionId);
                registrationActionRouter.setResponse(response);
                return registrationActionRouter.call();
            }

            case MAIN -> {
                MainMenuActionRouter mainMenuAction = beanFactory.getBean(MainMenuActionRouter.class);
                mainMenuAction.setDispatchObject(dispatchObject);
                mainMenuAction.setSessionId(sessionId);
                mainMenuAction.setResponse(response);
                mainMenuAction.setUser(dispatchObject.getSession().getUser());
                return mainMenuAction.call();
            }

            case PURCHASE_MAIN -> {
                MainMenuActionRouter mainMenuAction = beanFactory.getBean(MainMenuActionRouter.class);
                mainMenuAction.setDispatchObject(dispatchObject);
                mainMenuAction.setSessionId(sessionId);
                mainMenuAction.setResponse(response);
                mainMenuAction.setUser(dispatchObject.getSession().getUser());
                return mainMenuAction.call();
            }

            case WALLET -> {
                WalletActionRouter walletActionRouter = beanFactory.getBean(WalletActionRouter.class);
                walletActionRouter.setDispatchObject(dispatchObject);
                walletActionRouter.setSessionId(sessionId);
                walletActionRouter.setResponse(response);
                walletActionRouter.setUser(dispatchObject.getSession().getUser());
                return walletActionRouter.call();
            }

            case ORDER_HISTORY -> {
                MainMenuActionRouter mainMenuAction = beanFactory.getBean(MainMenuActionRouter.class);
                mainMenuAction.setDispatchObject(dispatchObject);
                mainMenuAction.setSessionId(sessionId);
                mainMenuAction.setResponse(response);
                mainMenuAction.setUser(dispatchObject.getSession().getUser());
                return mainMenuAction.call();
            }

            case ACCOUNT -> {
                MainMenuActionRouter mainMenuAction = beanFactory.getBean(MainMenuActionRouter.class);
                mainMenuAction.setDispatchObject(dispatchObject);
                mainMenuAction.setSessionId(sessionId);
                mainMenuAction.setResponse(response);
                mainMenuAction.setUser(dispatchObject.getSession().getUser());
                return mainMenuAction.call();
            }

            case REPORT_INCIDENT -> {
                MainMenuActionRouter mainMenuAction = beanFactory.getBean(MainMenuActionRouter.class);
                mainMenuAction.setDispatchObject(dispatchObject);
                mainMenuAction.setSessionId(sessionId);
                mainMenuAction.setResponse(response);
                mainMenuAction.setUser(dispatchObject.getSession().getUser());
                return mainMenuAction.call();
            }


        }


        return null;
    }

}

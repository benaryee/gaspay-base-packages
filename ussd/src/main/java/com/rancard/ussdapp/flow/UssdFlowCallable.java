package com.rancard.ussdapp.flow;


import com.rancard.ussdapp.flow.actions.InvalidOptionAction;
import com.rancard.ussdapp.flow.actions.enquiry.MainEnquiryAction;
import com.rancard.ussdapp.flow.actions.enquiry.EnquiryActionRouter;
import com.rancard.ussdapp.model.payload.DispatchObject;
import com.rancard.ussdapp.model.response.UssdResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

import static com.rancard.ussdapp.model.enums.MenuLevel.ENQUIRY;

@Slf4j
@Component
@RequiredArgsConstructor
public class UssdFlowCallable implements Callable<UssdResponse> {

    private String sessionId;

    private HttpServletRequest httpServletRequest;
    private UssdResponse response;
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
                        dispatchObject.getSession().setMenuLevel(ENQUIRY);
                        MainEnquiryAction mainEnquiryAction = beanFactory.getBean(MainEnquiryAction.class);
                        mainEnquiryAction.setDispatchObject(dispatchObject);
                        mainEnquiryAction.setSessionId(sessionId);
                        mainEnquiryAction.setResponse(response);
                        return mainEnquiryAction.call();
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
                EnquiryActionRouter enquiryActionRouter = beanFactory.getBean(EnquiryActionRouter.class);
                enquiryActionRouter.setDispatchObject(dispatchObject);
                enquiryActionRouter.setSessionId(sessionId);
                enquiryActionRouter.setResponse(response);
                return enquiryActionRouter.call();
            }


        }


        return null;
    }

}

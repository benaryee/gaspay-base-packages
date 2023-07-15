package com.rancard.ussdapp.botlets;


import com.rancard.ussdapp.botlets.actions.InvalidOptionAction;
import com.rancard.ussdapp.model.payload.DispatchObject;
import com.rancard.ussdapp.model.response.UssdResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderingBotCallable implements Callable<UssdResponse> {

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
                dispatchObject.getSession().getOptions().size() < Integer.parseInt(dispatchObject.getUssdRequest().getMessage())){

            InvalidOptionAction invalidOptionAction = beanFactory.getBean(InvalidOptionAction.class);
            invalidOptionAction.setDispatchObject(dispatchObject);
            invalidOptionAction.setSessionId(sessionId);
            invalidOptionAction.setResponse(response);
            return invalidOptionAction.call();

        }


        return null;
    }

}

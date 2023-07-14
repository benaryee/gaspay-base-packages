package com.rancard.ussdapp.services;


import com.rancard.ussdapp.botlets.OrderingBotCallable;
import com.rancard.ussdapp.model.mongo.User;
import com.rancard.ussdapp.model.payload.DispatchObject;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.services.menumanagement.AccountCreationManager;
import com.rancard.ussdapp.services.menumanagement.UnregisteredUserManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestDispatcher {

    private UssdResponse response = new UssdResponse();
    private final UserService userService;
    private final AccountCreationManager accountCreationManager;
    private final InvalidOptionManager invalidOptionManager;
    private final UnregisteredUserManager unregisteredUserManager;
    private final OrderingBotCallable orderingBotCallable;

    public UssdResponse handleInitialRequest(DispatchObject dispatchObject,HttpServletRequest servletRequest , String sessionId){

        log.info("[{}] checking if user with msisdn {} is existing customer", sessionId , dispatchObject.getUssdRequest().getMsisdn());
        User user = userService.findUserByMsisdn(dispatchObject.getUssdRequest().getMsisdn(), sessionId);

        if(user == null){
         return unregisteredUserManager.handleInitialRequest(dispatchObject,response , sessionId);
        }else{
          return null;
        }
    }

    public UssdResponse processRequest(DispatchObject dispatchObject, HttpServletRequest servletRequest, String sessionId){

        log.info("[{}] about to process request for {} using entry {} at menu level ",sessionId,
                dispatchObject.getUssdRequest().getMsisdn() , dispatchObject.getUssdRequest().getMessage());
        log.info("[{}] about to dispatch request per menu level: {} " , sessionId , dispatchObject.getSession().getMenuLevel());

        switch (dispatchObject.getSession().getMenuLevel()){
            case UNREGISTERED_USER_INITIAL_REQUEST -> {
                switch (dispatchObject.getUssdRequest().getMessage()){
                    case "1"->{
                        log.info("[{}] user has opted for for account creation process", sessionId);

                        break;
                    }
                    case "2"->{
                        log.info("[{}] user has opted to make enquiry" , sessionId);
                        break;
                    }
                    default ->{
                        log.info("[{}] user entered wrong input", sessionId);
                        try {
                            response = orderingBotCallable.execute(dispatchObject,servletRequest, response,sessionId);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            }


        }
        return  response;
    }

}

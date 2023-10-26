package com.rancard.ussdapp.services;


import com.rancard.ussdapp.flow.UssdFlowCallable;
import com.rancard.ussdapp.model.dto.UserDto;
import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.enums.SubMenuLevel;
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

    public UssdResponse response;
    private final UserService userService;
    private final UnregisteredUserManager unregisteredUserManager;
    private final UssdFlowCallable ussdFlowCallable;

    public UssdResponse handleInitialRequest(DispatchObject dispatchObject,HttpServletRequest servletRequest , String sessionId){

        log.info("[{}] checking if user with msisdn {} is existing customer", sessionId , dispatchObject.getUssdRequest().getMsisdn());
        UserDto user = userService.findUserByMsisdn(dispatchObject.getUssdRequest().getMsisdn(), sessionId);

        if(user == null){
         return unregisteredUserManager.handleInitialRequest(dispatchObject,response , sessionId);
        }else{
            try {
                dispatchObject.getSession().setMenuLevel(MenuLevel.MAIN);
                dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.MAIN);
                dispatchObject.getSession().setUser(user);
              return  response = ussdFlowCallable.execute(dispatchObject,sessionId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public UssdResponse processRequest(DispatchObject dispatchObject, HttpServletRequest servletRequest, String sessionId){

        log.info("[{}] about to process request for {} using entry {} at menu level ",sessionId,
                dispatchObject.getUssdRequest().getMsisdn() , dispatchObject.getUssdRequest().getMessage());
        log.info("[{}] about to dispatch request per menu level: {} " , sessionId , dispatchObject.getSession().getMenuLevel());

        try {
            response = ussdFlowCallable.execute(dispatchObject,sessionId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return  response;
    }

    public void setResponse(UssdResponse response){
        this.response = response;
    }
}

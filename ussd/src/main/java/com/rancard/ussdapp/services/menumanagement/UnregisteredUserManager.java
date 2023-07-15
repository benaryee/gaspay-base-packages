package com.rancard.ussdapp.services.menumanagement;


import com.rancard.ussdapp.model.enums.MenuLevel;
import com.rancard.ussdapp.model.payload.DispatchObject;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnregisteredUserManager {

    private final MenuUtils menuUtils;

    public UssdResponse handleInitialRequest(DispatchObject dispatchObject,UssdResponse response , String sessionId){
        dispatchObject.getSession().setMenuLevel(MenuLevel.UNREGISTERED_USER_INITIAL_REQUEST);
        log.info("[{}] found unregistered user about to return new user menu :" , sessionId);
        response.setMessage(menuUtils.getResponse(dispatchObject,sessionId));
        response.setContinueSession(true);
        return response;
    }

}

package com.gaspay.ussdapp.services.menumanagement;


import com.gaspay.ussdapp.model.enums.MenuKey;
import com.gaspay.ussdapp.model.enums.MenuLevel;
import com.gaspay.ussdapp.model.payload.DispatchObject;
import com.gaspay.ussdapp.model.response.UssdResponse;
import com.gaspay.ussdapp.utils.MenuUtils;
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
        dispatchObject.getSession().setMenuKey(MenuKey.UNREGISTERED_USER_INITIAL_RESPONSE);
        log.info("[{}] found unregistered user about to return new user menu " , sessionId);
        response.setMessage(menuUtils.getResponse(dispatchObject,sessionId));
        response.setContinueSession(true);
        return response;
    }

}

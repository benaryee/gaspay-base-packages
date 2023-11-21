package com.gaspay.ussdapp.flow.actions;


import com.gaspay.ussdapp.model.response.UssdResponse;
import com.gaspay.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.gaspay.ussdapp.model.enums.MenuKey.INVALID_USER_SELECTION_RESPONSE;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class InvalidOptionAction extends BotletActions{


    public InvalidOptionAction(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call(){
        response.setContinueSession(true);
        response.setMessage(menuUtils.getResponse(INVALID_USER_SELECTION_RESPONSE,dispatchObject,sessionId));
        response.setMessage(response.getMessage().replace("[min]", "1").replace("[max]", String.valueOf(dispatchObject.getSession().getOptions().size())));
        log.info("[{}] invalid option response : {}", sessionId , response);
        return response;

    }

}

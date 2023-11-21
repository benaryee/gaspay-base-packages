package com.gaspay.ussdapp.flow.actions;


import com.gaspay.ussdapp.model.dto.UserDto;
import com.gaspay.ussdapp.model.payload.DispatchObject;
import com.gaspay.ussdapp.model.response.UssdResponse;
import com.gaspay.ussdapp.utils.MenuUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class BotletActions {

    protected final BeanFactory beanFactory;

    protected UserDto user;
    protected HttpServletRequest httpServletRequest;
    protected String message;
    protected String payload;
    protected final MenuUtils menuUtils;

    protected String sessionId;

    protected DispatchObject dispatchObject;
    protected UssdResponse response;


}

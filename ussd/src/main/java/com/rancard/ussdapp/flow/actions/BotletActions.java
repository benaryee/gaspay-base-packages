package com.rancard.ussdapp.flow.actions;


import com.rancard.ussdapp.model.dto.UserDto;
import com.rancard.ussdapp.model.mongo.User;
import com.rancard.ussdapp.model.payload.DispatchObject;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.services.UserService;
import com.rancard.ussdapp.utils.MenuUtils;
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

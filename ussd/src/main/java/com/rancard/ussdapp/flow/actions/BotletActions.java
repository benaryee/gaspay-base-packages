package com.rancard.ussdapp.flow.actions;


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

//
//    @Value("${rgw.base.url}")
//    protected String RGW_BASE_URL;
//
//    @Value("${email.to.exceptions}")
//    protected String exceptionsTo;
//
//    @Value("${sms.url}")
//    protected String SEND_SMS_URL;

    protected User user;
    protected HttpServletRequest httpServletRequest;
    protected String message;
    protected String payload;
    protected final MenuUtils menuUtils;

    protected String sessionId;

    protected DispatchObject dispatchObject;
    protected UssdResponse response;


}

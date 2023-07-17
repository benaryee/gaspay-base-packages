package com.rancard.ussdapp.flow.actions.account.registration;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.mongo.User;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.services.UserService;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class PreviousActionResponseHandler extends BotletActions {

    protected final UserService userService;


    public PreviousActionResponseHandler(BeanFactory beanFactory, MenuUtils menuUtils, UserService userService) {
        super(beanFactory, menuUtils);
        this.userService = userService;
    }

    public void call() {

        log.info("[{}] registration request router received request to route registration request : {} ", sessionId, dispatchObject.toString());

        if(dispatchObject.getSession().getPreviousSubMenuLevel() == null)
            return;

        switch(dispatchObject.getSession().getPreviousSubMenuLevel()) {

            case REGISTRATION_FIRST_NAME -> {
                dispatchObject.getSession().setUser(new User());
                dispatchObject.getSession().getUser().setMsisdn(dispatchObject.getUssdRequest().getMsisdn());
                dispatchObject.getSession().getUser().setFirstname(dispatchObject.getUssdRequest().getMessage());
                break;
            }

            case REGISTRATION_LAST_NAME -> {
                dispatchObject.getSession().getUser().setLastname(dispatchObject.getUssdRequest().getMessage());
                break;
            }

            case REGISTRATION_ADDRESS -> {
                dispatchObject.getSession().getUser().getAddress().setLocation(dispatchObject.getUssdRequest().getMessage());
                break;
            }


            case REGISTRATION_CURRENT_FUEL_SOURCE -> {
                dispatchObject.getSession().getUser().setCurrentFuelSource(dispatchObject.getSession().getOptions()
                        .get(Integer.parseInt(dispatchObject.getUssdRequest().getMessage())));
                break;
            }

            case REGISTRATION_FAMILY_SIZE -> {
                dispatchObject.getSession().getUser().setCurrentFuelSource(dispatchObject.getSession().getOptions()
                        .get(Integer.parseInt(dispatchObject.getUssdRequest().getMessage())));
                userService.registerUser(dispatchObject.getSession().getUser(), sessionId);
                break;
            }

            case REGISTRATION_COMPLETE -> {

            }

        }
    }

}

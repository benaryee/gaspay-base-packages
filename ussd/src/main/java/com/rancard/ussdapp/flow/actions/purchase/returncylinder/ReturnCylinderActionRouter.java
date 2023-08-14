package com.rancard.ussdapp.flow.actions.purchase.returncylinder;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.flow.actions.purchase.PreviousPurchaseActionResponseHandler;
import com.rancard.ussdapp.flow.actions.purchase.refillcylinder.RefillCylinderActionRouter;
import com.rancard.ussdapp.model.enums.SubMenuLevel;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.PURCHASE_MAIN_MENU_RESPONSE;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.PURCHASE_MAIN_MENU;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class ReturnCylinderActionRouter extends BotletActions {


    public ReturnCylinderActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call() {

        PreviousPurchaseActionResponseHandler previousPurchaseActionResponseHandler = beanFactory.getBean(PreviousPurchaseActionResponseHandler.class);
        previousPurchaseActionResponseHandler.setDispatchObject(dispatchObject);
        previousPurchaseActionResponseHandler.setSessionId(sessionId);
        previousPurchaseActionResponseHandler.call();



        log.info("[{}] enquiry request router received request to route enquiry request : {} ", sessionId, dispatchObject.toString());
        switch (dispatchObject.getSession().getSubMenuLevel()) {

            case PURCHASE_MAIN_MENU -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(PURCHASE_MAIN_MENU_RESPONSE,dispatchObject,sessionId));
                log.info("[{}] Purchase main menu submenuLevel response : {}", sessionId , response);
                dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.PURCHASE_MAIN_MENU_RESPONSE);
                dispatchObject.getSession().setPreviousSubMenuLevel(PURCHASE_MAIN_MENU);
                return response;
            }
            case PURCHASE_MAIN_MENU_RESPONSE ->{
                switch(dispatchObject.getUssdRequest().getMessage()){
                    case "1"->{
                        //Purchase a Cylinder
                    }
                    case "2"->{
                     //Refill a Cylinder

                    }
                    case "3" -> {
                        //Return a Cylinder
                    }
                    case "4" -> {
                        //Help
                    }
                }
            }


        }
        return null;
    }

}
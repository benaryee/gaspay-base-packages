package com.rancard.ussdapp.flow.actions.purchase;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.flow.actions.purchase.refillcylinder.RefillCylinderActionRouter;
import com.rancard.ussdapp.flow.actions.wallet.PreviousWalletActionResponseHandler;
import com.rancard.ussdapp.model.enums.SubMenuLevel;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.*;
import static com.rancard.ussdapp.model.enums.MenuKey.PURCHASE_MAIN_MENU_RESPONSE;
import static com.rancard.ussdapp.model.enums.MenuLevel.PURCHASE_REFILL;
import static com.rancard.ussdapp.model.enums.MenuLevel.WALLET;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class PurchaseActionRouter extends BotletActions {


    public PurchaseActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call() {

        PreviousPurchaseActionResponseHandler previousPurchaseActionResponseHandler = beanFactory.getBean(PreviousPurchaseActionResponseHandler.class);
        previousPurchaseActionResponseHandler.setDispatchObject(dispatchObject);
        previousPurchaseActionResponseHandler.setSessionId(sessionId);
        previousPurchaseActionResponseHandler.call();

        if (dispatchObject.getSession().isThrowPreviousMenuError()){
            response.setMessage(menuUtils.getResponse(dispatchObject.getSession().getMenuKey(),dispatchObject,sessionId));
            response.setContinueSession(true);
            dispatchObject.getSession().setThrowPreviousMenuError(false);
            return response;
        }

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
                        RefillCylinderActionRouter refillCylinderActionRouter = beanFactory.getBean(RefillCylinderActionRouter.class);
                        dispatchObject.getSession().setSubMenuLevel(REFILL_MAIN_MENU);
                        dispatchObject.getSession().setMenuLevel(PURCHASE_REFILL);
                        refillCylinderActionRouter.setDispatchObject(dispatchObject);
                        refillCylinderActionRouter.setSessionId(sessionId);
                        refillCylinderActionRouter.setResponse(response);
                        refillCylinderActionRouter.setUser(dispatchObject.getSession().getUser());
                        return refillCylinderActionRouter.call();
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

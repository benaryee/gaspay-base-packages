package com.rancard.ussdapp.flow.actions.main;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.flow.actions.myaccount.MyAccountActionRouter;
import com.rancard.ussdapp.flow.actions.orderhistory.OrderHistoryAction;
import com.rancard.ussdapp.flow.actions.purchase.PurchaseActionRouter;
import com.rancard.ussdapp.flow.actions.wallet.WalletActionRouter;
import com.rancard.ussdapp.model.enums.SubMenuLevel;
import com.rancard.ussdapp.model.response.UssdResponse;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.*;
import static com.rancard.ussdapp.model.enums.MenuKey.MAIN_MENU_RESPONSE;
import static com.rancard.ussdapp.model.enums.MenuLevel.*;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class MainMenuActionRouter extends BotletActions {


    public MainMenuActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call() {

        switch (dispatchObject.getSession().getSubMenuLevel()) {

            case MAIN -> {
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(MAIN_MENU_RESPONSE, dispatchObject, sessionId).replace("[name]", user.getFirstname()));
                log.info("[{}] Main menu submenuLevel response : {}", sessionId, response);
                dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.MAIN_MENU_RESPONSE);
                dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.MAIN);
                return response;
            }


            case MAIN_MENU_RESPONSE -> {
                switch (dispatchObject.getUssdRequest().getMessage()) {
                    case "1" -> {
                        PurchaseActionRouter purchaseActionRouter = beanFactory.getBean(PurchaseActionRouter.class);
                        dispatchObject.getSession().setSubMenuLevel(PURCHASE_MAIN_MENU);
                        dispatchObject.getSession().setMenuLevel(PURCHASE);
                        purchaseActionRouter.setDispatchObject(dispatchObject);
                        purchaseActionRouter.setSessionId(sessionId);
                        purchaseActionRouter.setResponse(response);
                        purchaseActionRouter.setUser(dispatchObject.getSession().getUser());
                        return purchaseActionRouter.call();
                    }
                    case "2" -> {
                        WalletActionRouter walletActionRouter = beanFactory.getBean(WalletActionRouter.class);
                        dispatchObject.getSession().setSubMenuLevel(TOPUP_AMOUNT);
                        dispatchObject.getSession().setMenuLevel(WALLET);
                        walletActionRouter.setDispatchObject(dispatchObject);
                        walletActionRouter.setSessionId(sessionId);
                        walletActionRouter.setResponse(response);
                        walletActionRouter.setUser(dispatchObject.getSession().getUser());
                        return walletActionRouter.call();
                    }
                    case "3" -> {
                        MyAccountActionRouter myAccountActionRouter = beanFactory.getBean(MyAccountActionRouter.class);
                        dispatchObject.getSession().setSubMenuLevel(MY_ACCOUNT_MAIN_MENU);
                        dispatchObject.getSession().setMenuLevel(ACCOUNT);
                        myAccountActionRouter.setDispatchObject(dispatchObject);
                        myAccountActionRouter.setSessionId(sessionId);
                        myAccountActionRouter.setResponse(response);
                        myAccountActionRouter.setUser(dispatchObject.getSession().getUser());
                        return myAccountActionRouter.call();
                    }
                    case "4" -> {
                        OrderHistoryAction orderHistoryAction = beanFactory.getBean(OrderHistoryAction.class);
                        dispatchObject.getSession().setSubMenuLevel(VIEW_FULL_ORDER_HISTORY);
                        orderHistoryAction.setDispatchObject(dispatchObject);
                        orderHistoryAction.setSessionId(sessionId);
                        orderHistoryAction.setResponse(response);
                        orderHistoryAction.setUser(dispatchObject.getSession().getUser());
                        return orderHistoryAction.call();
                    }
                    case "5" -> {
                        response.setContinueSession(true);
                        response.setMessage(menuUtils.getResponse(UNDER_CONSTRUCTION_RESPONSE, dispatchObject, sessionId));
                        log.info("[{}] Main menu submenuLevel response : {}", sessionId, response);
                        dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.MAIN_MENU_RESPONSE);
                        dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.MAIN);
                        return response;
                    }

                }
            }
        }
        return null;
    }
}

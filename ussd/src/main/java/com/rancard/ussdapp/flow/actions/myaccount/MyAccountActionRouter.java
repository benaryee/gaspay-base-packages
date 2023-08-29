package com.rancard.ussdapp.flow.actions.myaccount;


import com.rancard.ussdapp.flow.actions.BotletActions;
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
import static com.rancard.ussdapp.model.enums.MenuLevel.PURCHASE;
import static com.rancard.ussdapp.model.enums.MenuLevel.WALLET;
import static com.rancard.ussdapp.model.enums.SubMenuLevel.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class MyAccountActionRouter extends BotletActions {


    public MyAccountActionRouter(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
    }

    public UssdResponse call() {

        switch (dispatchObject.getSession().getSubMenuLevel()) {
            case MY_ACCOUNT_MAIN_MENU:
                response.setContinueSession(true);
                response.setMessage(menuUtils.getResponse(MY_ACCOUNT_MAIN_MENU_RESPONSE, dispatchObject, sessionId));
                log.info("[{}] Main menu submenuLevel response : {}", sessionId, response);
                dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.MAIN_MENU_RESPONSE);
                dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.MY_ACCOUNT_MAIN_MENU);
                return response;

        }
        return null;
    }
}

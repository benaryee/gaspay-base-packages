package com.gaspay.ussdapp.flow.actions.purchase.purchasecylinder;


import com.gaspay.ussdapp.flow.actions.BotletActions;
import com.gaspay.ussdapp.services.PaymentService;
import com.gaspay.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class PreviousPurchaseClinderActionResponseHandler extends BotletActions {

    private final PaymentService paymentService;


    public PreviousPurchaseClinderActionResponseHandler(BeanFactory beanFactory, MenuUtils menuUtils, PaymentService paymentService) {
        super(beanFactory, menuUtils);

        this.paymentService = paymentService;
    }

    public void call() {

        log.info("[{}] registration request router received request to route registration request : {} ", sessionId, dispatchObject.toString());

        if (dispatchObject.getSession().getPreviousSubMenuLevel() == null)
            return;

        switch (dispatchObject.getSession().getPreviousSubMenuLevel()) {

        }

    }
}

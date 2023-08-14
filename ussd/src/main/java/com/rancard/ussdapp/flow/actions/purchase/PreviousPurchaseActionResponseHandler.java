package com.rancard.ussdapp.flow.actions.purchase;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.dto.TopupRequestDto;
import com.rancard.ussdapp.model.enums.Channel;
import com.rancard.ussdapp.services.PaymentService;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.rancard.ussdapp.model.enums.MenuKey.TOPUP_AMOUNT_INVALID;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class PreviousPurchaseActionResponseHandler extends BotletActions {

    private final PaymentService paymentService;


    public PreviousPurchaseActionResponseHandler(BeanFactory beanFactory, MenuUtils menuUtils, PaymentService paymentService) {
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

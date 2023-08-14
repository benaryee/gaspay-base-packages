package com.rancard.ussdapp.flow.actions.purchase.refillcylinder;


import com.rancard.ussdapp.flow.actions.BotletActions;
import com.rancard.ussdapp.model.dto.CreateOrderDto;
import com.rancard.ussdapp.model.dto.OrderItemDto;
import com.rancard.ussdapp.services.PaymentService;
import com.rancard.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class PreviousRefillActionResponseHandler extends BotletActions {

    private final PaymentService paymentService;


    public PreviousRefillActionResponseHandler(BeanFactory beanFactory, MenuUtils menuUtils, PaymentService paymentService) {
        super(beanFactory, menuUtils);

        this.paymentService = paymentService;
    }

    public void call() {

        log.info("[{}] registration request router received request to route registration request : {} ", sessionId, dispatchObject.toString());

        if (dispatchObject.getSession().getPreviousSubMenuLevel() == null)
            return;

        switch (dispatchObject.getSession().getPreviousSubMenuLevel()) {
            case REFILL_MAIN_MENU -> {
                CreateOrderDto createOrderDto = new CreateOrderDto();
                createOrderDto.setCustomerMsisdn(dispatchObject.getUssdRequest().getMsisdn());
                OrderItemDto orderItemDto = new OrderItemDto();
                orderItemDto.setSize(dispatchObject.getSession().getOptions().get(Integer.parseInt(dispatchObject.getUssdRequest().getMessage())));
                createOrderDto.setOrderItemsDtoList(Collections.singletonList(orderItemDto));

                dispatchObject.getSession().setOrderDto(createOrderDto);

            }
        }

    }
}

package com.gaspay.ussdapp.flow.actions.purchase.refillcylinder;


import com.gaspay.ussdapp.flow.actions.BotletActions;
import com.gaspay.ussdapp.model.dto.CreateOrderDto;
import com.gaspay.ussdapp.model.dto.OrderItemDto;
import com.gaspay.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class PreviousRefillActionResponseHandler extends BotletActions {



    public PreviousRefillActionResponseHandler(BeanFactory beanFactory, MenuUtils menuUtils) {
        super(beanFactory, menuUtils);
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
                orderItemDto.setSize(dispatchObject.getSession().getVariantOptions().get(Integer.parseInt(dispatchObject.getUssdRequest().getMessage())).getWeight().toString());
                orderItemDto.setPrice(dispatchObject.getSession().getVariantOptions().get(Integer.parseInt(dispatchObject.getUssdRequest().getMessage())).getPrice());
                orderItemDto.setQuantity(1f);
                orderItemDto.setSkuCode(orderItemDto.getSize()+"kg_cylinder");
                createOrderDto.setOrderItemsDtoList(Collections.singletonList(orderItemDto));

                dispatchObject.getSession().setOrderDto(createOrderDto);
                createOrderDto.setTotalAmount(orderItemDto.getPrice());
                dispatchObject.getSession().setOrderDto(createOrderDto);

            }
            case REFILL_DIGITAL_ADDRESS -> {
                dispatchObject.getSession().getOrderDto().setShippingAddress(dispatchObject.getSession().getUser().getAddress());
            }
        }

    }
}

package com.gaspay.ussdapp.flow.actions.orderhistory;

import com.gaspay.ussdapp.flow.actions.BotletActions;
import com.gaspay.ussdapp.model.dto.OrderDto;
import com.gaspay.ussdapp.model.enums.SubMenuLevel;
import com.gaspay.ussdapp.model.payload.DispatchObject;
import com.gaspay.ussdapp.model.payload.Option;
import com.gaspay.ussdapp.model.response.ApiResponse;
import com.gaspay.ussdapp.model.response.UssdResponse;
import com.gaspay.ussdapp.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.gaspay.ussdapp.model.enums.MenuKey.ORDER_HISTORY_FULL_ORDER_HISTORY_RESPONSE;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class OrderHistoryAction extends BotletActions {

    private final WebClient.Builder webClientBuilder;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public OrderHistoryAction(BeanFactory beanFactory, MenuUtils menuUtils, WebClient.Builder webClientBuilder) {
        super(beanFactory, menuUtils);
        this.webClientBuilder = webClientBuilder;
    }

    public UssdResponse call() {


        switch (dispatchObject.getSession().getSubMenuLevel()) {

            case VIEW_FULL_ORDER_HISTORY -> {
                response.setContinueSession(true);
                ApiResponse<List<OrderDto>> orderHistoryOptions = getUserOrderHistory(dispatchObject);

                if(orderHistoryOptions != null && orderHistoryOptions.getData() != null && orderHistoryOptions.getData().size() > 0){
                    List<OrderDto> userOrders =  orderHistoryOptions.getData();
                    StringBuilder orderHistory = new StringBuilder();
                    List<Option> pseudoOptions  = new ArrayList<>();
                    int count = 1;
                    for (OrderDto userOrder : userOrders) {
                        Option pseudoOption = new Option();
                        orderHistory.append(count).append(". ").append(userOrder.get_created().format(formatter)).append(" - ").append(userOrder.getTotalAmount()).append("\n");
                        pseudoOption.setId(count);
                        pseudoOption.setHandler(userOrder.getOrderId());
                        pseudoOptions.add(pseudoOption);
                        count++;
                    }

                    log.info(orderHistory.toString());
                    response.setMessage(menuUtils.getResponse(ORDER_HISTORY_FULL_ORDER_HISTORY_RESPONSE, dispatchObject, sessionId,pseudoOptions).replace("[orderHistory]", orderHistory.toString()));
                    log.info("[{}] Main menu submenuLevel response : {}", sessionId, response);
                    dispatchObject.getSession().setSubMenuLevel(SubMenuLevel.FULL_ORDER_HISTORY_RESPONSE);
                    dispatchObject.getSession().setPreviousSubMenuLevel(SubMenuLevel.VIEW_FULL_ORDER_HISTORY);
                }else{
                    response.setContinueSession(false);
                    response.setMessage(menuUtils.getResponse(ORDER_HISTORY_FULL_ORDER_HISTORY_RESPONSE, dispatchObject, sessionId).replace("[orderHistory]", "You have no order history"));
                }
                return response;

            }

        }
        return response;
    }

    private ApiResponse<List<OrderDto>> getUserOrderHistory(DispatchObject dispatchObject) {

        //Call order service to get user order history
        return webClientBuilder.build().get()
                .uri("lb://reporting-service/api/report/customer/{msisdn}", dispatchObject.getUssdRequest().getMsisdn())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<OrderDto>>>() {})
                .block();
    }
}
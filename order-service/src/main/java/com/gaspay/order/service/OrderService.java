package com.gaspay.order.service;



import com.gaspay.order.event.OrderPlacedEvent;
import com.gaspay.order.model.mongo.Agent;
import com.gaspay.order.model.response.ApiResponse;
import com.gaspay.order.repository.OrderRepository;
import com.rancard.dto.OrderItemDto;
import com.rancard.dto.OrderRequest;
import com.rancard.enums.OrderStatus;
import com.rancard.event.OrderEvent;
import com.rancard.mongo.Order;
import com.rancard.payload.OrderItem;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

import static com.rancard.enums.OrderStatus.PENDING;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final ObservationRegistry observationRegistry;
    private final ApplicationEventPublisher applicationEventPublisher;

    private NewTopic topic;

    public Order placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setCustomerMsisdn(orderRequest.getCustomerMsisdn());
        order.setOrderStatus(PENDING);

        List<OrderItem> orderLineItems = orderRequest.getOrderItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setItems(orderLineItems);

        Agent agent = getAgent();
        if(agent != null){
            order.setAgentId(agent.getMsisdn());
        }

//        List<String> skuCodes = order.getItems().stream()
//                .map(OrderItem::getSkuCode)
//                .toList();

        // Call Inventory Service, and place order if product is in
        // stock
//        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
//                this.observationRegistry);
//        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
//        return inventoryServiceObservation.observe(() -> {
//            InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
//                    .uri("http://inventory-service/api/inventory",
//                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
//                    .retrieve()
//                    .bodyToMono(InventoryResponse[].class)
//                    .block();

            boolean allProductsInStock = true;
//            boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
//                    .allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {
                orderRepository.save(order);
                // publish Order Placed Event
                applicationEventPublisher.publishEvent(new OrderPlacedEvent(this, new OrderEvent("New Order Event Received", PENDING, order.toDto())));
                return order;
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
//        });

    }

    private OrderItem mapToDto(OrderItemDto orderLineItemsDto) {
        OrderItem orderLineItems = new OrderItem();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }



    public Agent getAgent(){

        ApiResponse<?> agentResponse = webClientBuilder.build().get()
                .uri("lb://auth/api/auth/users/agent")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Agent>>() {})
                .block();

        if(agentResponse != null && agentResponse.getData() != null){
            return (Agent) agentResponse.getData();
        }
        return null;

    }
}

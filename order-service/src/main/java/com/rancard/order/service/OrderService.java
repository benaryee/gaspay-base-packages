package com.rancard.order.service;


import com.rancard.basepackages.model.enums.OrderStatus;
import com.rancard.basepackages.model.mongo.Agent;
import com.rancard.order.dto.OrderItemDto;
import com.rancard.order.dto.OrderRequest;
import com.rancard.order.event.OrderPlacedEvent;
import com.rancard.order.model.Order;
import com.rancard.order.model.OrderItem;
import com.rancard.order.model.response.ApiResponse;
import com.rancard.order.repository.OrderRepository;
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
        order.setShippingAddress(order.getShippingAddress());
        order.setCustomerMsisdn(orderRequest.getCustomerMsisdn());
        order.setOrderStatus(OrderStatus.PENDING);

        List<OrderItem> orderLineItems = orderRequest.getOrderItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setItems(orderLineItems);

        Agent agent = getAgent();
        if(agent != null){
            order.setAgentId(agent.getIdString());
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
                applicationEventPublisher.publishEvent(new OrderPlacedEvent(this, order));
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

    public ApiResponse<?> getOrdersByCustomer(String customerMsisdn) {
        log.info("Getting Orders By Customer");
        List<Order> orders = orderRepository.findByCustomerMsisdn(customerMsisdn);
        return ApiResponse.builder()
                .data(orders)
                .message("Orders Retrieved Successfully")
                .code(200)
                .build();
    }

    public Order getOrder(String orderId) {
        log.info("Getting Order");
        return orderRepository.findById(orderId).orElseThrow();
    }

    public List<Order> getOrders() {
        log.info("Getting Order");
        return orderRepository.findAll();
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

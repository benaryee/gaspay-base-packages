package com.gaspay.reportingservice.service;


import com.gaspay.reportingservice.repository.OrderDao;
import com.rancard.dto.OrderDto;
import com.rancard.mongo.Order;
import com.rancard.response.ApiResponse;
import com.rancard.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderReportService {

    private final OrderDao orderDao;

    public void saveOrder(OrderDto orderDto) {
        log.info("Saving order: " + orderDto);
        Order order = Order.fromDto(orderDto);
        orderDao.save(order);
    }

    private OrderDto mapOrderToDto(Order order) {
        return OrderDto.builder()
                .createdAt(order.getCreated())
                .items(order.getItems())
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .shippingAddress(order.getShippingAddress())
                .totalAmount(order.getTotalAmount())
                .customerMsisdn(order.getCustomerMsisdn())
                .agentId(order.getAgentId())
                .build();
    }

    public ApiResponse<?> getOrdersByCustomer(String customerMsisdn,String sessionId) {
        log.info("Getting Orders By Customer");
        List<Order> orders = orderDao.findByCustomerMsisdn(customerMsisdn);
        List<OrderDto> orderDtos = orders
                .stream()
                .map(this::mapOrderToDto)
                .toList();

        return ApiUtils.wrapInApiResponse(orderDtos,sessionId);
    }

    public OrderDto getOrder(String orderId) {
        log.info("Getting Order");
        Order order  = orderDao.findByOrderId(orderId);
        return mapOrderToDto(order);
    }

    public List<OrderDto> getOrders(String agentId) {
        log.info("Getting Order");
        List<Order> orders = orderDao.findByAgentId(agentId);
        List<OrderDto> orderDtos = orders
                .stream()
                .map(this::mapOrderToDto)
                .toList();
        return orderDtos;
    }
}

package com.rancard.reportingservice.service;

import com.rancard.basepackages.dto.OrderDto;
import com.rancard.basepackages.mongo.Order;
import com.rancard.basepackages.response.ApiResponse;
import com.rancard.reportingservice.repository.OrderDao;
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

    public ApiResponse<?> getOrdersByCustomer(String customerMsisdn) {
        log.info("Getting Orders By Customer");
        List<Order> orders = orderDao.findByCustomerMsisdn(customerMsisdn);
        List<OrderDto> orderDtos = orders
                .stream()
                .map(this::mapOrderToDto)
                .toList();

        return ApiResponse.builder()
                .data(orderDtos)
                .message("Orders Retrieved Successfully")
                .code(200)
                .build();
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

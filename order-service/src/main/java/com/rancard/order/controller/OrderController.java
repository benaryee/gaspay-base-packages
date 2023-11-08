package com.rancard.order.controller;


import com.rancard.order.dto.OrderDto;
import com.rancard.order.dto.OrderRequest;
import com.rancard.order.model.Order;
import com.rancard.order.model.response.ApiResponse;
import com.rancard.order.service.OrderService;
import com.rancard.order.util.ApiUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> placeOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        log.info("Placing Order");
        Order order = orderService.placeOrder(orderRequest);
        return ApiUtils.wrapInApiResponse(order, sessionId);
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        log.info("Cannot Place Order Executing Fallback logic");
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
    }

    @GetMapping("/agent/{agentId}")
    public ApiResponse<?> getOrders(@PathVariable String agentId,HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        log.info("Getting Orders");
        List<OrderDto> orders = orderService.getOrders(agentId);
        return ApiUtils.wrapInApiResponse(orders,sessionId);
    }

    @GetMapping("/{orderId}")
    public CompletableFuture<Order> getOrder(@PathVariable String orderId) {
        log.info("Getting Order");
        return CompletableFuture.supplyAsync(() -> orderService.getOrder(orderId));
    }

    @GetMapping("/customer/{customerMsisdn}")
    public CompletableFuture<ApiResponse<?>> getOrdersByCustomer(@PathVariable String customerMsisdn) {
        log.info("Getting Orders By Customer");
        return CompletableFuture.supplyAsync(() -> orderService.getOrdersByCustomer(customerMsisdn));
    }


}

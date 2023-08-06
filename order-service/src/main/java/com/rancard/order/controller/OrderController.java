package com.rancard.order.controller;


import com.rancard.order.dto.OrderRequest;
import com.rancard.order.model.Order;
import com.rancard.order.model.response.ApiResponse;
import com.rancard.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
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
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Placing Order");
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        log.info("Cannot Place Order Executing Fallback logic");
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
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

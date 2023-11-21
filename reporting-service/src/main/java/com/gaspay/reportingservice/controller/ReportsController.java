package com.gaspay.reportingservice.controller;


import com.gaspay.reportingservice.service.OrderReportService;
import com.rancard.dto.OrderDto;
import com.rancard.response.ApiResponse;
import com.rancard.utils.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportsController {

    private final OrderReportService orderReportService;


    @GetMapping("/agent/{agentId}")
    public ApiResponse<?> getOrders(@PathVariable String agentId, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        log.info("Getting Orders");
        List<OrderDto> orders = orderReportService.getOrders(agentId);
        return ApiUtils.wrapInApiResponse(orders,sessionId);
    }

    @GetMapping("/customer/{customerMsisdn}")
    public CompletableFuture<ApiResponse<?>> getOrdersByCustomer(@PathVariable String customerMsisdn, HttpServletRequest request) {
        String sessionId = request.getSession().getId();

        log.info("Getting Orders By Customer");
        return CompletableFuture.supplyAsync(() -> orderReportService.getOrdersByCustomer(customerMsisdn,sessionId));
    }


    @GetMapping("/{orderId}")
    public CompletableFuture<OrderDto> getOrder(@PathVariable String orderId) {
        log.info("Getting Order");
        return CompletableFuture.supplyAsync(() -> orderReportService.getOrder(orderId));
    }





}

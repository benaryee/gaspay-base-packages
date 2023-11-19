package com.rancard.order.controller;



import com.rancard.basepackages.dto.OrderDto;
import com.rancard.basepackages.dto.OrderRequest;
import com.rancard.basepackages.mongo.Order;
import com.rancard.order.model.response.ApiResponse;
import com.rancard.order.service.OrderService;
import com.rancard.order.util.ApiUtils;
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

}

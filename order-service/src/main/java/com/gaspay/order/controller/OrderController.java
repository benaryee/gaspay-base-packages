package com.gaspay.order.controller;


import com.gaspay.order.model.response.ApiResponse;
import com.gaspay.order.service.OrderService;
import com.gaspay.order.util.ApiUtils;
import com.rancard.dto.OrderRequest;
import com.rancard.mongo.Order;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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

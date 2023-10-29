package com.rancard.reportingservice.controller;

import com.rancard.reportingservice.model.Order;
import com.rancard.reportingservice.service.OrderReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportsController {

    private final OrderReportService orderReportService;


    @GetMapping("/orders")
    public List<Order> getOrders() {
        log.info("Getting Orders");
       return orderReportService.getOrders();
    }

}

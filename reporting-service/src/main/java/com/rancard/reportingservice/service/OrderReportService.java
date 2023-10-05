package com.rancard.reportingservice.service;

import com.rancard.reportingservice.model.Order;
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

    public void saveOrder(Order order) {
        log.info("Saving order: " + order);
        orderDao.save(order);
    }

    public List<Order> getOrders() {
        log.info("Getting orders");
        return orderDao.findAll();
    }

}

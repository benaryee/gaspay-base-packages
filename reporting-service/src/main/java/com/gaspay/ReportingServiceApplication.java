package com.gaspay;

import com.gaspay.reportingservice.service.OrderReportService;
import com.rancard.event.OrderEvent;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class ReportingServiceApplication {

    private final ObservationRegistry observationRegistry;
    private final Tracer tracer;

    private final OrderReportService orderReportService;

    public static void main(String[] args) {
        SpringApplication.run(ReportingServiceApplication.class, args);
    }


    @KafkaListener(topics = "orderTopic")
    public void orderPlacedMessage(OrderEvent orderEvent) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Got message <{}>", orderEvent);
            log.info("TraceId- {}, Received Notification for Order - {}", this.tracer.currentSpan().context().traceId(),
                    orderEvent.getOrderDto());
            orderReportService.saveOrder(orderEvent.getOrderDto());
        });

        // send out an email notification
    }
}
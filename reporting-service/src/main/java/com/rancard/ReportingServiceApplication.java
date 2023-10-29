package com.rancard;

import com.rancard.reportingservice.model.OrderPlacedEvent;
import com.rancard.reportingservice.service.OrderReportService;
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
    public void orderPlacedMessage(OrderPlacedEvent orderPlacedEvent) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Got message <{}>", orderPlacedEvent);
            log.info("TraceId- {}, Received Notification for Order - {}", this.tracer.currentSpan().context().traceId(),
                    orderPlacedEvent.getOrder());
            orderReportService.saveOrder(orderPlacedEvent.getOrder());
        });

        // send out an email notification
    }
}
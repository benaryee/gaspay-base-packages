package com.rancard;

import com.pusher.rest.Pusher;
import com.rancard.util.MailUtils;
import com.rancard.util.SmsUtils;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceApplication {

    private final ObservationRegistry observationRegistry;
    private final Tracer tracer;
    private final SmsUtils smsUtils;



    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Got message <{}>", orderPlacedEvent);
            log.info("TraceId- {}, Received Notification for Order - {}", this.tracer.currentSpan().context().traceId(),
                    orderPlacedEvent.getOrder());
        });

    }

    @KafkaListener(topics = "orderTopic")
    public void handleOrder(OrderPlacedEvent orderPlacedEvent) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Got message <{}>", orderPlacedEvent);

            //Send Agent SMS
            smsUtils.sendSms(orderPlacedEvent.getOrder().getAgentId(), "You have received a new order");
            //Send User SMS
            smsUtils.sendSms(orderPlacedEvent.getOrder().getAgentId(), "Your order has been received. An agent will contact you shortly");
            //Push to Portal
            //Use pusher to push to portal
            Pusher pusher = new Pusher("951394", "bed5f50e8d6138946013", "05091c00189a606dc1ac");
            pusher.setCluster("ap3");
            pusher.setEncrypted(true);

            pusher.trigger(orderPlacedEvent.getOrder().getAgentId(), "new-order", Collections.singletonMap("message", "hello world"));


            //Send Email
            String htmlContent = getOrderEmail()
                    .replace("{{orderId}}", orderPlacedEvent.getOrder().getOrderId())
                    .replace("{{date}}", new Date().toString())
                    .replace("{{amount}}", String.valueOf(orderPlacedEvent.getOrder().getTotalAmount()))
                            .replace("{{contact}}", orderPlacedEvent.getOrder().getCustomerMsisdn())
                                    .replace("{{street}}", orderPlacedEvent.getOrder().getShippingAddress().getStreet())
                                            .replace("{{gps}}", orderPlacedEvent.getOrder().getShippingAddress().getGhanaPostGps());

            MailUtils.sendNotification("bernard.aryee@rancard.com", "New Order", htmlContent);

            log.info("TraceId- {}, Received Notification for Order - {}", this.tracer.currentSpan().context().traceId(),
                    orderPlacedEvent.getOrder());
        });

    }


    private String getOrderEmail(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Order Confirmation</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "        <tr>\n" +
                "            <td align=\"center\">\n" +
                "                <h1>Order Confirmation</h1>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    \n" +
                "    <table width=\"100%\" cellpadding=\"10\" cellspacing=\"0\">\n" +
                "        <tr>\n" +
                "            <td>\n" +
                "                <h2>Order Details</h2>\n" +
                "                <p><strong>Order Number:</strong> {{orderId}}</p>\n" +
                "                <p><strong>Order Date:</strong> {{date}}</p>\n" +
                "                <p><strong>Total Amount:</strong> GHs{{amount}}</p>\n" +
                "                <p><strong>Customer Contact:</strong> {{contact}}</p>\n" +
                "            </td>\n" +
                "            <td>\n" +
                "                <h2>Shipping Address</h2>\n" +
                "                <p><strong>Street Name:</strong> {{street}}</p>\n" +
                "                <p><strong>GPS Location:</strong> {{gps}}</p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "</html>\n";
    }

}

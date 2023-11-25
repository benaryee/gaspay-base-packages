package com.gaspay.paymentservice.model.mongo;

public enum PaymentStatus {
    PENDING("Pending"),



    SUCCESS("Success"),
    FAILED("Failed"),
    CANCELLED("Cancelled"),
    REVERSAL("Reversal"),
    ERROR("Error");

    PaymentStatus(String status) {
    }
}

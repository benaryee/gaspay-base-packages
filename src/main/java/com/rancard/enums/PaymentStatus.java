package com.rancard.enums;

public enum PaymentStatus {
    PENDING("Pending"),
    SUCCESS("Success"),
    FAILED("Failed"),
    CANCELLED("Cancelled"),
    REVERSAL("Reversal"),
    ERROR("Error");

    PaymentStatus(String status) {
    }

    public static PaymentStatus fromString(String text) {
        for (PaymentStatus b : PaymentStatus.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public static PaymentStatus fromStringWithDefault(String text, PaymentStatus defaultStatus) {
        for (PaymentStatus b : PaymentStatus.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return defaultStatus;
    }

    public static PaymentStatus fromStringWithDefault(String text, String defaultStatus) {
        for (PaymentStatus b : PaymentStatus.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return fromString(defaultStatus);
    }

    public static PaymentStatus fromStringWithDefault(String text, PaymentStatus defaultStatus, PaymentStatus defaultStatus2) {
        for (PaymentStatus b : PaymentStatus.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return defaultStatus;
    }


}

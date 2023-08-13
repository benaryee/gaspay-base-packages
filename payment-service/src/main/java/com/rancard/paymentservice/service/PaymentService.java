package com.rancard.paymentservice.service;

import com.rancard.paymentservice.model.dto.wallet.TopupupRequestDto;
import com.rancard.paymentservice.model.mongo.Payment;

public interface PaymentService {

    Payment makePayment(TopupupRequestDto topupupRequestDto, String sessionId);
}

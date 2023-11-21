package com.gaspay.paymentservice.service;

import com.gaspay.paymentservice.model.dto.CallbackRequest;
import com.gaspay.paymentservice.model.dto.wallet.TopupupRequestDto;
import com.gaspay.paymentservice.model.mongo.Payment;

public interface PaymentService {

    Payment makePayment(TopupupRequestDto topupupRequestDto, String sessionId);

    Payment processCallback(CallbackRequest callbackRequest, String sessionId);
}

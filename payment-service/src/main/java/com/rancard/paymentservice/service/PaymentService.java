package com.rancard.paymentservice.service;

import com.rancard.paymentservice.model.dto.wallet.CreditWalletDto;
import com.rancard.paymentservice.model.mongo.Payment;

public interface PaymentService {
    Payment topupWallet(CreditWalletDto creditWalletDto, String sessionId);
}

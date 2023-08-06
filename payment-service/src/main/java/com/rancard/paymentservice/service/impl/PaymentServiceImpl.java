package com.rancard.paymentservice.service.impl;

import com.rancard.paymentservice.model.dto.wallet.CreditWalletDto;
import com.rancard.paymentservice.model.mongo.Payment;
import com.rancard.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Override
    public Payment topupWallet(CreditWalletDto creditWalletDto, String sessionId) {
        return null;
    }
}

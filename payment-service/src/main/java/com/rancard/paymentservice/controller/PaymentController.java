package com.rancard.paymentservice.controller;

import com.rancard.paymentservice.model.domain.ApiResponse;
import com.rancard.paymentservice.model.dto.wallet.CreditWalletDto;
import com.rancard.paymentservice.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/topup")
    public ApiResponse<?> topupWallet(@RequestBody CreditWalletDto creditWalletDto, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return ApiResponse.builder()
                .code(200)
                .message("Wallet debited successfully")
                .data(paymentService.topupWallet(creditWalletDto, sessionId))
                .build();
    }

}

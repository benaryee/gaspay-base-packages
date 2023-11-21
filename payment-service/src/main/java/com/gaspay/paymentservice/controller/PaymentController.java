package com.gaspay.paymentservice.controller;

import com.gaspay.paymentservice.model.dto.CallbackRequest;
import com.gaspay.paymentservice.service.ZeepayOAuth2Config;
import com.gaspay.paymentservice.model.domain.ApiResponse;
import com.gaspay.paymentservice.model.dto.wallet.TopupupRequestDto;
import com.gaspay.paymentservice.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final ZeepayOAuth2Config zeepayOAuth2Config;

    @PostMapping("/topup")
    public ApiResponse<?> topupWallet(@RequestBody TopupupRequestDto topupupRequestDto, HttpServletRequest request) {
        String sessionId = UUID.randomUUID().toString();
        return ApiResponse.builder()
                .code(200)
                .message("Wallet debited successfully")
                .data(paymentService.makePayment(topupupRequestDto, sessionId))
                .build();
    }

    @PostMapping("/topup/callback")
    public ApiResponse<?> topupWallet(@RequestBody CallbackRequest callbackRequest, HttpServletRequest request) {
        String sessionId = UUID.randomUUID().toString();
        log.info("["+sessionId+"] to process callback :"+callbackRequest);
        return ApiResponse.builder()
                .code(200)
                .data(paymentService.processCallback(callbackRequest, sessionId))
                .message("Wallet debited successfully")
                .build();
    }

}

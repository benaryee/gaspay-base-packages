package com.rancard.paymentservice.controller;

import com.rancard.paymentservice.model.dto.CallbackRequest;
import com.rancard.paymentservice.service.ZeepayOAuth2Config;
import com.rancard.paymentservice.model.domain.ApiResponse;
import com.rancard.paymentservice.model.dto.wallet.TopupupRequestDto;
import com.rancard.paymentservice.service.PaymentService;
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

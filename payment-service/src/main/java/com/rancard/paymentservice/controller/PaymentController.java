package com.rancard.paymentservice.controller;

import com.rancard.paymentservice.service.ZeepayOAuth2Config;
import com.rancard.paymentservice.model.domain.ApiResponse;
import com.rancard.paymentservice.model.dto.wallet.TopupupRequestDto;
import com.rancard.paymentservice.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final ZeepayOAuth2Config zeepayOAuth2Config;

    @PostMapping("/topup")
    public ApiResponse<?> topupWallet(@RequestBody TopupupRequestDto topupupRequestDto, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return ApiResponse.builder()
                .code(200)
                .message("Wallet debited successfully")
                .data(paymentService.makePayment(topupupRequestDto, sessionId))
                .build();
    }


}

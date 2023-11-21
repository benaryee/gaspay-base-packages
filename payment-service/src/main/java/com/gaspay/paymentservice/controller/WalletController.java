package com.gaspay.paymentservice.controller;

import com.gaspay.paymentservice.model.domain.ApiResponse;
import com.gaspay.paymentservice.model.dto.wallet.*;
import com.gaspay.paymentservice.model.mongo.Wallet;
import com.gaspay.paymentservice.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/payment/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final ModelMapper modelMapper;

    @PostMapping()
    public ApiResponse<?> createWallet(@RequestBody CreateWalletDto createWalletDto) {
        log.info("Creating wallet for phone number: {}", createWalletDto.getWalletId());
        Wallet wallet = walletService.createWallet(createWalletDto);
        WalletDto walletDto = modelMapper.map(wallet, WalletDto.class);

        ApiResponse<?> response = ApiResponse.builder()
                .code(200)
                .message("Wallet created successfully")
                .data(walletDto)
                .build();

        log.info("Wallet created successfully: {}", response);
        return response;
    }

    @GetMapping("{id}")
    public ApiResponse<?> getWallet(@PathVariable String id , HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        log.info(" [{}] Fetching wallet with id: {}", sessionId,id);
        Wallet wallet = walletService.getWallet(id);
        WalletDto walletDto = modelMapper.map(wallet, WalletDto.class);

        return ApiResponse.<WalletDto>builder()
                .code(200)
                .message("Wallet fetched successfully")
                .data(walletDto)
                .build();
    }

    @GetMapping
    public ApiResponse<?> getAllWallets() {
        return ApiResponse.builder()
                .code(200)
                .message("Wallet fetched successfully")
                .data(walletService.getAllWallets())
                .build();
    }

    @PostMapping("/credit")
    public ApiResponse<?> creditWallet(@RequestBody CreditWalletDto creditWalletDto , HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return ApiResponse.builder()
                .code(200)
                .message("Wallet debited successfully")
                .data(walletService.creditWallet(creditWalletDto , sessionId))
                .build();
    }

    @PostMapping("/debit")
    public ApiResponse<?> debitWallet(@RequestBody DebitWalletDto debitWalletDto , HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return ApiResponse.builder()
                .code(200)
                .message("Wallet debited successfully")
                .data(walletService.debitWallet(debitWalletDto , sessionId))
                .build();
    }



}

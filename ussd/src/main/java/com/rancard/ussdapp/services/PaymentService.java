package com.rancard.ussdapp.services;

import com.rancard.ussdapp.exception.ServiceException;
import com.rancard.ussdapp.exception.ServiceExceptionResponse;
import com.rancard.ussdapp.model.dto.CreditWalletDto;
import com.rancard.ussdapp.model.dto.DebitWalletDto;
import com.rancard.ussdapp.model.dto.TopupRequestDto;
import com.rancard.ussdapp.model.dto.WalletResponseDto;
import com.rancard.ussdapp.model.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final WebClient.Builder webClientBuilder;

    public ApiResponse<?> sendPaymentRequestToUser(TopupRequestDto topupRequestDto, String sessionId){
        log.info("[{}] about to send payment request to user with payload : {}",sessionId , topupRequestDto  );

        return webClientBuilder.build().post()
                .uri("lb://payment-service/api/payment/topup")
                .bodyValue(topupRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {})
                .block();
    }

    public WalletResponseDto getBalance(String walletId, String sessionId) {

        ApiResponse<WalletResponseDto> walletResponse = webClientBuilder.build().get()
                .uri("lb://payment-service/api/payment/wallet/"+walletId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<WalletResponseDto>>() {})
                .block();

        log.info("[{}] Response from payment service : {}",sessionId, walletResponse);
        return Objects.requireNonNull(walletResponse).getData();
    }

    public ApiResponse<WalletResponseDto> deductFromWallet(String walletId, BigDecimal amount, String sessionId) {

        DebitWalletDto debitWalletDto = new DebitWalletDto();
        debitWalletDto.setAmount(amount);
        debitWalletDto.setId(walletId);
        ApiResponse<WalletResponseDto> walletResponse = null;
        try {
         walletResponse = webClientBuilder.build().post()
                    .uri("lb://payment-service/api/payment/wallet/debit")
                    .bodyValue(debitWalletDto)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<WalletResponseDto>>() {
                    })
                    .block();
        }catch (ServiceException serviceException){
            if(serviceException.getCode() == 4002){
                log.info("Insufficient Balance");
            }
        }catch (WebClientResponseException.InternalServerError internalServerError){
            internalServerError.getMessage();
            internalServerError.getResponseBodyAs(ServiceExceptionResponse.class);
        }

        return walletResponse;
    }

    public WalletResponseDto creditWallet(String walletId, BigDecimal amount, String sessionId) {

        CreditWalletDto creditWalletDto = new CreditWalletDto();
        creditWalletDto.setAmount(amount);
        creditWalletDto.setId(walletId);

        ApiResponse<WalletResponseDto> walletResponse = webClientBuilder.build().post()
                .uri("lb://payment-service/api/payment/wallet/credit")
                .bodyValue(creditWalletDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<WalletResponseDto>>() {})
                .block();

        log.info("[{}] Response from payment service : {}",sessionId, walletResponse);
        return Objects.requireNonNull(walletResponse).getData();
    }
}

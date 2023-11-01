package com.rancard.paymentservice.service.impl;

import com.rancard.paymentservice.exception.ServiceException;
import com.rancard.paymentservice.model.domain.ZeepayApiRequest;
import com.rancard.paymentservice.model.domain.ZeepayApiResponse;
import com.rancard.paymentservice.model.dto.CallbackRequest;
import com.rancard.paymentservice.model.dto.wallet.CreditWalletDto;
import com.rancard.paymentservice.model.mongo.PaymentStatus;
import com.rancard.paymentservice.model.mongo.PaymentType;
import com.rancard.paymentservice.repository.PaymentRepository;
import com.rancard.paymentservice.repository.WalletRepository;
import com.rancard.paymentservice.service.WalletService;
import com.rancard.paymentservice.service.WebClientService;
import com.rancard.paymentservice.service.ZeepayOAuth2Config;
import com.rancard.paymentservice.model.dto.wallet.TopupupRequestDto;
import com.rancard.paymentservice.model.mongo.Payment;
import com.rancard.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.rancard.paymentservice.model.enums.ServiceError.PAYMENT_FAILED;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final WalletService walletService;
    private final ZeepayOAuth2Config zeepayOAuth2Config;
    private final WebClientService webClientService;
    private final PaymentRepository paymentRepository;

    @Value("${zeepay.oauth2.client.base-uri}")
    private String BASE_URL;

    @Value("${zeepay.payment.callback-uri}")
    private String CALLBACK_URL;


    @Override
    public Payment makePayment(TopupupRequestDto topupupRequestDto, String sessionId) {
        log.info("[{}] about to topup wallet with payload : {}", sessionId, topupupRequestDto);

        //Make API call to Zeepay
        HttpMethod method = HttpMethod.POST;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(zeepayOAuth2Config.getAccessTokenObject().getAccessToken());

        ZeepayApiRequest requestBody = ZeepayApiRequest.builder()
                .description("Topup for "+ topupupRequestDto.getUser().getPhone())
                .amount(topupupRequestDto.getAmount())
                .extr_id(UUID.randomUUID().toString().replace("-","").substring(0,  19))
                .source_country("GHA")
                .service_type("wallet")
                .customer_first_name(topupupRequestDto.getUser().getFirstname())
                .customer_last_name(topupupRequestDto.getUser().getLastname())
                .debit_currency("GHS")
                .debit_country("GHA")
                .customer_msisdn(topupupRequestDto.getUser().getPhone())
                .mno(getMno(topupupRequestDto.getMobileNetwork()))
                .transaction_type("DR")
                .callback_url(CALLBACK_URL)
                .build();
        ParameterizedTypeReference<ZeepayApiResponse> responseType = new ParameterizedTypeReference<>() {};

        log.info("[{}] request body to zeepay ::: {}",sessionId,requestBody.toString());

        ZeepayApiResponse response = webClientService.makeApiCall(BASE_URL+"/api/debits", method, null, headers, requestBody, responseType);

        log.info("[{}] response from ZEEPAY : {} ", sessionId , response);

        if(response.getCode() != null && response.getCode().equals("411")){
            log.info("[{}] Pending user approval : {} ", sessionId , response);
            Payment payment = Payment.builder()
                    .paymentId(response.getZeepayId())
                    .walletId(topupupRequestDto.getUser().getWalletId())
                    .paymentType(PaymentType.TOPUP)
                    .amount(topupupRequestDto.getAmount())
                    .currency("GHS")
                    .status(PaymentStatus.PENDING)
                    .sessionId(sessionId)
                    .build();
            return paymentRepository.save(payment);
        }
        throw new ServiceException(PAYMENT_FAILED);
    }

    @Override
    public Payment processCallback(CallbackRequest callbackRequest, String sessionId) {
        log.info("[{}] about to process callback with payload : {}", sessionId, callbackRequest);
        if(callbackRequest.getPaymentId() == null){
            throw new ServiceException(PAYMENT_FAILED);
        }

        Payment payment = paymentRepository.findByPaymentId(callbackRequest.getPaymentId())
                .orElseThrow(() -> new ServiceException(PAYMENT_FAILED));

        if(payment.getStatus().equals(PaymentStatus.PENDING)){
            CreditWalletDto creditWalletDto = CreditWalletDto.builder()
                    .amount(payment.getAmount())
                    .id(payment.getWalletId())
                    .build();

            walletService.creditWallet(creditWalletDto, sessionId);
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setSessionId(sessionId);
        }

        return paymentRepository.save(payment);
    }

    private String getMno(String mobileNetwork) {
        return switch (mobileNetwork) {
            case "MTNGH" -> "MTN";
            case "OT" -> "VODAFONE";
            default -> mobileNetwork;
        };
    }

}

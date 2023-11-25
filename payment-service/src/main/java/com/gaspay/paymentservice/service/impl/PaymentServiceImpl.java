package com.gaspay.paymentservice.service.impl;

import com.gaspay.paymentservice.exception.ServiceException;
import com.gaspay.paymentservice.model.domain.ZeepayApiRequest;
import com.gaspay.paymentservice.model.domain.ZeepayApiResponse;
import com.gaspay.paymentservice.model.dto.CallbackRequest;
import com.gaspay.paymentservice.model.dto.wallet.CreditWalletDto;
import com.gaspay.paymentservice.model.mongo.PaymentStatus;
import com.gaspay.paymentservice.model.mongo.PaymentType;
import com.gaspay.paymentservice.repository.PaymentRepository;
import com.gaspay.paymentservice.service.WalletService;
import com.gaspay.paymentservice.service.WebClientService;
import com.gaspay.paymentservice.service.ZeepayOAuth2Config;
import com.gaspay.paymentservice.model.dto.wallet.TopupupRequestDto;
import com.gaspay.paymentservice.model.mongo.Payment;
import com.gaspay.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.gaspay.paymentservice.model.enums.ServiceError.PAYMENT_FAILED;

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

        HttpMethod method = HttpMethod.POST;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjI4OGVhYTQ5MzQ0YzFjYzBjYzNhNjA3ZmJiNmQ2ZTgzZDYwOTc1MjVhODQ1MGUwZGNkNGZkMTQ0MzA4OWJkY2UxYWVkN2JhNGY1ZjUzYmZlIn0.eyJhdWQiOiIyNDMiLCJqdGkiOiIyODhlYWE0OTM0NGMxY2MwY2MzYTYwN2ZiYjZkNmU4M2Q2MDk3NTI1YTg0NTBlMGRjZDRmZDE0NDMwODliZGNlMWFlZDdiYTRmNWY1M2JmZSIsImlhdCI6MTcwMDY2MDI5MCwibmJmIjoxNzAwNjYwMjkwLCJleHAiOjE3MzIyODI2OTAsInN1YiI6IjMwNiIsInNjb3BlcyI6WyIqIl19.h3eBw_tXLfaDVIdiLJiSFQxsX4ZMIdPzVHqixiXRhv1Bz5RYRqzCIJhBoAwlfpQud5yCMKOfOYVmWt4x6G6w7CaEhW7N6L9vImtZLJTKu3VN4hVwvvZXj6objWVgRTpn4Yl_3ax9whjADyOgmd7035KP8VDTI7sucSdH5C9K9oWermu7bIGXyRws0XjugN22T8uk54_jdVv1kgpY3PbpQA-y7BQgeXEUTTeOjMv4Pd3-Zy87rRLJvomRbKBB7aijcCSLTPvMR1ZCroDBzWhGLekn0Gkl8kH0QUm2izx7SwiaLLT1rw7r7PdN60X7vvv2TvR7a6bt2on6PmqD0JVn2dNC_ik1oimnInGU-8Xda0PLweMUNmmvGBWNK0ST7JfOn4sPqpGsj-R0r7ZHUSrKMjqAKxhGDymH63LYTsLARaV7egODqRlVAotfad-0K71U9enwz5wVc7NzzEZv2RLmkq4atA5iRD0lUjlXhyvgW5U6q3cUxPy1m_CA08ZihI_oeptPrL8XdigHeepO7b8lei49i05oEtGD5X1AT1qnobJS9Jtx2ig9G2a_BbuTfeMjpzA9SBP-hgCqJB-hb4sy1JQl35tpTSRv2K7gEnUobWomVwAgiPglXfo4MEZsPly0Iac6guujem7t8nZsMq8UVcJYDlhyl0igDrmnC_6hWHc");

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

        if(payment.getStatus().equals(PaymentStatus.valueOf("Success"))){
            CreditWalletDto creditWalletDto = CreditWalletDto.builder()
                    .amount(payment.getAmount())
                    .id(payment.getWalletId())
                    .build();

            walletService.creditWallet(creditWalletDto, sessionId);
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setSessionId(sessionId);
        }else{
            payment.setStatus(PaymentStatus.valueOf(callbackRequest.getStatus()));
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

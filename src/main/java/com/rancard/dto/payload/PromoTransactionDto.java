/*(C) Gaspay App 2024 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.TRANSACTION_RESULT;
import com.rancard.enums.TRANSACTION_STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PromoTransactionDto {

    private String id;
    private String msisdn;
    private String channel;
    private String transaction_time;

    private TRANSACTION_RESULT transaction_result;
    private String hashedCode;

    private String code;
    private double amount;
    private TRANSACTION_STATUS status;
    private String network;
    private String transactionId;
    private String campaignId;
    private String station;
    private String product;
    private String sku;

    private String sessionId;
    private String rewardType;
    private String topupUrl;
    private String topupAccount;
    private String redisKey;
    private boolean drawWinner;
}

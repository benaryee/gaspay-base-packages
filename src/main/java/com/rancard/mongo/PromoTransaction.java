/*(C) Gaspay App 2024 */
package com.rancard.mongo;

import com.rancard.dto.payload.PromoTransactionDto;
import com.rancard.enums.TRANSACTION_RESULT;
import com.rancard.enums.TRANSACTION_STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "PromoTransaction")
public class PromoTransaction extends BaseMongoModel {

    @Indexed private String msisdn;

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

    public static PromoTransaction fromDto(PromoTransactionDto promoTransactionDto) {
        return promoTransactionDto != null
                ? PromoTransaction.builder()
                        .id(new ObjectId(promoTransactionDto.getId()))
                        .msisdn(promoTransactionDto.getMsisdn())
                        .channel(promoTransactionDto.getChannel())
                        .transaction_time(promoTransactionDto.getTransaction_time())
                        .transaction_result(promoTransactionDto.getTransaction_result())
                        .hashedCode(promoTransactionDto.getHashedCode())
                        .code(promoTransactionDto.getCode())
                        .amount(promoTransactionDto.getAmount())
                        .status(promoTransactionDto.getStatus())
                        .network(promoTransactionDto.getNetwork())
                        .transactionId(promoTransactionDto.getTransactionId())
                        .campaignId(promoTransactionDto.getCampaignId())
                        .station(promoTransactionDto.getStation())
                        .product(promoTransactionDto.getProduct())
                        .sku(promoTransactionDto.getSku())
                        .sessionId(promoTransactionDto.getSessionId())
                        .rewardType(promoTransactionDto.getRewardType())
                        .topupUrl(promoTransactionDto.getTopupUrl())
                        .topupAccount(promoTransactionDto.getTopupAccount())
                        .redisKey(promoTransactionDto.getRedisKey())
                        .drawWinner(promoTransactionDto.isDrawWinner())
                        .build()
                : null;
    }

    public PromoTransactionDto toDto() {
        return PromoTransactionDto.builder()
                .id(getIdString())
                .msisdn(msisdn)
                .channel(channel)
                .transaction_time(transaction_time)
                .transaction_result(transaction_result)
                .hashedCode(hashedCode)
                .code(code)
                .amount(amount)
                .status(status)
                .network(network)
                .transactionId(transactionId)
                .campaignId(campaignId)
                .station(station)
                .product(product)
                .sku(sku)
                .sessionId(sessionId)
                .rewardType(rewardType)
                .topupUrl(topupUrl)
                .topupAccount(topupAccount)
                .redisKey(redisKey)
                .drawWinner(drawWinner)
                .build();
    }
}

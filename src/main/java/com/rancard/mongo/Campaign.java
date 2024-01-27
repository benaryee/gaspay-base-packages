/*(C) Gaspay App 2023-2024 */
package com.rancard.mongo;

import com.rancard.dto.payload.CampaignDto;
import com.rancard.enums.CampaignType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Document
public class Campaign extends BaseMongoModel {

    private String name;
    private String customShareUrl;
    private String campaignId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private boolean paused;
    private String ussdCode;
    private CampaignType campaignType;
    private String rewardId;
    private Double balance;
    private Double allocation;

    public static Campaign fromDto(CampaignDto campaignDto) {
        return campaignDto != null
                ? Campaign.builder()
                        .id(new ObjectId(campaignDto.getId()))
                        .name(campaignDto.getName())
                        .customShareUrl(campaignDto.getCustomShareUrl())
                        .campaignId(campaignDto.getCampaignId())
                        .startDate(campaignDto.getStartDate())
                        .endDate(campaignDto.getEndDate())
                        .ussdCode(campaignDto.getUssdCode())
                        .campaignType(campaignDto.getCampaignType())
                        .rewardId(campaignDto.getRewardId())
                        .active(campaignDto.isActive())
                        .balance(campaignDto.getBalance())
                        .allocation(campaignDto.getAllocation())
                        .build()
                : null;
    }

    public CampaignDto toDto() {
        return CampaignDto.builder()
                .id(getIdString())
                .name(name)
                .customShareUrl(customShareUrl)
                .campaignId(campaignId)
                .startDate(startDate)
                .endDate(endDate)
                .rewardId(rewardId)
                .active(active || (startDate.isBefore(LocalDateTime.now()) && endDate.isAfter(LocalDateTime.now())) || !paused )
                .ussdCode(ussdCode)
                .campaignType(campaignType)
                .balance(balance)
                .allocation(allocation)
                .build();
    }
}

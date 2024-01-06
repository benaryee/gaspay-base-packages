/*(C) Gaspay App 2023 */
package com.rancard.mongo;

import com.rancard.dto.payload.CampaignDto;
import com.rancard.enums.CampaignType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.time.DateTime;
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
    private DateTime startDate;
    private DateTime endDate;
    private String ussdCode;
    private CampaignType campaignType;
    private Double balance;
    private Double allocation;

    public CampaignDto toDto() {
        return CampaignDto.builder()
                .name(name)
                .customShareUrl(customShareUrl)
                .campaignId(campaignId)
                .startDate(startDate)
                .endDate(endDate)
                .campaignType(campaignType)
                .balance(balance)
                .allocation(allocation)
                .build();
    }

    public static Campaign fromDto(CampaignDto dto) {
        return Campaign.builder()
                .name(dto.getName())
                .customShareUrl(dto.getCustomShareUrl())
                .campaignId(dto.getCampaignId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .campaignType(dto.getCampaignType())
                .balance(dto.getBalance())
                .allocation(dto.getAllocation())
                .build();
    }
}

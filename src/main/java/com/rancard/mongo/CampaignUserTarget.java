package com.rancard.mongo;

import com.rancard.dto.payload.CampaignTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Document
public class CampaignUserTarget extends BaseMongoModel {
    private String campaignId;
    private CampaignTarget campaignTarget;
}

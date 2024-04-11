package com.rancard.mongo;

import com.rancard.dto.payload.CampaignTarget;
import com.rancard.dto.payload.HirePurchaseTarget;
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
public class HirePurchaseUserTarget extends BaseMongoModel {
    private String hirePurchaseAgreementId;
    private HirePurchaseTarget hirePurchaseTarget;
}

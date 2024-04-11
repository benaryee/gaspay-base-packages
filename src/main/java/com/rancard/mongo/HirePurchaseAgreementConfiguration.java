package com.rancard.mongo;

import com.rancard.dto.payload.HirePurchaseAgreementConfigurationDto;
import com.rancard.dto.payload.ReminderSetting;
import com.rancard.dto.payload.TimeFrame;
import com.rancard.enums.AppAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Period;
import java.util.Map;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode(callSuper = true)
public class HirePurchaseAgreementConfiguration extends BaseMongoModel {
    private String agreementId;
    private Map<TimeFrame,Double> serviceChargePercentageByTimeFrame;
    private Map<AppAction, Object> eligibilityCriteria;
    private ReminderSetting reminderSetting;
    private Period maxHirePurchaseTenure;

    public static HirePurchaseAgreementConfiguration fromDto(HirePurchaseAgreementConfigurationDto hirePurchaseAgreementConfigurationDto){
        return HirePurchaseAgreementConfiguration.builder()
                .id(new ObjectId(hirePurchaseAgreementConfigurationDto.getId()))
                .agreementId(hirePurchaseAgreementConfigurationDto.getAgreementId())
                .serviceChargePercentageByTimeFrame(hirePurchaseAgreementConfigurationDto.getServiceChargePercentageByTimeFrame())
                .eligibilityCriteria(hirePurchaseAgreementConfigurationDto.getEligibilityCriteria())
                .reminderSetting(hirePurchaseAgreementConfigurationDto.getReminderSetting())
                .maxHirePurchaseTenure(hirePurchaseAgreementConfigurationDto.getMaxHirePurchaseTenure())
                .build();
    }

    public HirePurchaseAgreementConfigurationDto toDto(){
        return HirePurchaseAgreementConfigurationDto.builder()
                .id(getIdString())
                .agreementId(agreementId)
                .serviceChargePercentageByTimeFrame(serviceChargePercentageByTimeFrame)
                .eligibilityCriteria(eligibilityCriteria)
                .reminderSetting(reminderSetting)
                .maxHirePurchaseTenure(maxHirePurchaseTenure)
                .build();
    }
    
}

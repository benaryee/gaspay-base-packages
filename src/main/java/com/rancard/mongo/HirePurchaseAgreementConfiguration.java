package com.rancard.mongo;

import com.rancard.dto.payload.ReminderSetting;
import com.rancard.dto.payload.TimeFrame;
import com.rancard.enums.AppAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
    private String name;
    private String agreementId;
    private Map<TimeFrame,Double> serviceChargePercentageByTimeFrame;
    private Map<AppAction, Object> eligibilityCriteria;
    private ReminderSetting reminderSetting;
    private Period maxHirePurchaseTenure;
    
}

package com.rancard.dto.payload;

import com.rancard.enums.AppAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Period;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HirePurchaseAgreementConfigurationDto{
    private String id;
    private String name;
    private String agreementId;
    private Map<TimeFrame,Double> serviceChargePercentageByTimeFrame;
    private Map<AppAction, Object> eligibilityCriteria;
    private ReminderSetting reminderSetting;
    private Period maxHirePurchaseTenure;
    
}

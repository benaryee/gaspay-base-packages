package com.rancard.dto.request;

import com.rancard.dto.payload.ReminderSetting;
import com.rancard.dto.payload.TimeFrame;
import com.rancard.enums.AppAction;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Period;
import java.util.Map;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHirePurchaseAgreementDto implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private BigDecimal amount;

    @NotEmpty
    private Map<TimeFrame, Double> serviceChargePercentageByTimeFrame;

    private Map<AppAction, Object> eligibilityCriteria;

    private ReminderSetting reminderSetting;

    @NotNull
    private Period maxHirePurchaseTenure;
    private boolean generateCouponForGroup;
}

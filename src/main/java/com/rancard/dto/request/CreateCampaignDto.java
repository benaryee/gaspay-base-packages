/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.dto.payload.CampaignTarget;
import com.rancard.enums.CampaignType;
import com.rancard.enums.INCENTIVE_TRIGGER;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCampaignDto implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private CampaignType campaignType;
    private INCENTIVE_TRIGGER incentiveTrigger;
    private List<String> agentIds;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private List<CampaignTarget> campaignTargetList;

    @NotNull
    private String rewardId;

    private boolean generateCouponForGroup;
}

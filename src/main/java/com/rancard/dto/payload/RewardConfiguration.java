/*(C) Gaspay App 2024 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class RewardConfiguration {

    private double minimumPerUser;
    private double maximumPerUser;
    private double maximumPerCampaign;
    private double minimumPerWeek;
    private double maximumPerWeek;
    private double minimumPerMonth;
    private double maximumPerMonth;
    private double minimumPerUserPerWeek;
    private double maximumPerUserPerWeek;
    private double minimumPerUserPerMonth;
    private double maximumPerUserPerMonth;
    private double minimumPerUserPerCampaign;
    private double maximumPerUserPerCampaign;
}

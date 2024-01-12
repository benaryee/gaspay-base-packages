/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import com.rancard.dto.payload.CampaignTarget;
import com.rancard.dto.payload.RewardConfiguration;
import com.rancard.enums.CampaignType;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.List;

@Data
@Slf4j
@Builder
public class CreateCampaignDto {
    private String name;
    private CampaignType campaignType;
    private List<String> agentIds;
    private DateTime startDate;
    private DateTime endDate;
    private CampaignTarget campaignTarget;
    private RewardConfiguration rewardConfiguration;
}

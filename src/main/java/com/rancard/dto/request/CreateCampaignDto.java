/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import com.rancard.dto.payload.CampaignTarget;
import com.rancard.enums.CampaignType;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

@Data
@Slf4j
@Builder
public class CreateCampaignDto {
    private String name;
    private CampaignType campaignType;
    private String agentId;
    private DateTime startDate;
    private DateTime endDate;
    private CampaignTarget campaignTarget;
}

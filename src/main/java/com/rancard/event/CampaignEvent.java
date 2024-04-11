/*(C) Gaspay App 2023 */
package com.rancard.event;

import com.rancard.dto.payload.CampaignEventData;
import com.rancard.dto.payload.WalletEventData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class CampaignEvent extends ApplicationEvent {
    private CampaignEventData campaignEventData;

    public CampaignEvent() {
        super(new Object());
    }

    public CampaignEvent(Object source, CampaignEventData campaignEventData) {
        super(source);
        this.campaignEventData = campaignEventData;
    }

    public CampaignEvent(CampaignEventData campaignEventData) {
        super(campaignEventData);
        this.campaignEventData = campaignEventData;
    }
}

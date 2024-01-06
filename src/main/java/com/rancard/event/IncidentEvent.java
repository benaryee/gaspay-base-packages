/*(C) Gaspay App 2023 */
package com.rancard.event;

import com.rancard.dto.payload.IncidentEventData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class IncidentEvent extends ApplicationEvent {
    private IncidentEventData incidentEventData;

    public IncidentEvent() {
        super(new Object());
    }

    public IncidentEvent(Object source, IncidentEventData incidentEventData) {
        super(source);
        this.incidentEventData = incidentEventData;
    }

    public IncidentEvent(IncidentEventData incidentEventData) {
        super(incidentEventData);
        this.incidentEventData = incidentEventData;
    }
}

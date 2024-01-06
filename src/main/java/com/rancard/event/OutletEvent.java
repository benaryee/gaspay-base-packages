/*(C) Gaspay App 2023 */
package com.rancard.event;

import com.rancard.dto.payload.OutletEventData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class OutletEvent extends ApplicationEvent {
    private OutletEventData outletEventData;

    public OutletEvent() {
        super(new Object());
    }

    public OutletEvent(Object source, OutletEventData outletEventData) {
        super(source);
        this.outletEventData = outletEventData;
    }

    public OutletEvent(OutletEventData outletEventData) {
        super(outletEventData);
        this.outletEventData = outletEventData;
    }
}

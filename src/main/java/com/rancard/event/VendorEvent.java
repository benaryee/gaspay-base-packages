/*(C) Gaspay App 2023 */
package com.rancard.event;

import com.rancard.dto.payload.VendorEventData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class VendorEvent extends ApplicationEvent {
    private VendorEventData vendorEventData;

    public VendorEvent() {
        super(new Object());
    }

    public VendorEvent(Object source, VendorEventData vendorEventData) {
        super(source);
        this.vendorEventData = vendorEventData;
    }

    public VendorEvent(VendorEventData vendorEventData) {
        super(vendorEventData);
        this.vendorEventData = vendorEventData;
    }
}

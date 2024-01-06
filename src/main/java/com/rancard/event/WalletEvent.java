/*(C) Gaspay App 2023 */
package com.rancard.event;

import com.rancard.dto.payload.WalletEventData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class WalletEvent extends ApplicationEvent {
    private WalletEventData walletEventData;

    public WalletEvent() {
        super(new Object());
    }

    public WalletEvent(Object source, WalletEventData walletEventData) {
        super(source);
        this.walletEventData = walletEventData;
    }

    public WalletEvent(WalletEventData walletEventData) {
        super(walletEventData);
        this.walletEventData = walletEventData;
    }
}

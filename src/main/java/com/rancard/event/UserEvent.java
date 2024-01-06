/*(C) Gaspay App 2023 */
package com.rancard.event;

import com.rancard.dto.payload.UserEventData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class UserEvent extends ApplicationEvent {
    private UserEventData userEventData;

    public UserEvent() {
        super(new Object());
    }

    public UserEvent(Object source, UserEventData userEventData) {
        super(source);
        this.userEventData = userEventData;
    }

    public UserEvent(UserEventData userEventData) {
        super(userEventData);
        this.userEventData = userEventData;
    }
}

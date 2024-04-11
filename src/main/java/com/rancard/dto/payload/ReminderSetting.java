package com.rancard.dto.payload;

import com.rancard.enums.Frequency;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReminderSetting {
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Frequency frequency;
    private int numberOfTimes;

}

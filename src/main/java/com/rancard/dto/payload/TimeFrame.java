package com.rancard.dto.payload;

import lombok.Data;

import java.time.Period;

@Data
public class TimeFrame {
    private Period from;
    private Period to;
}

package com.rancard.dto.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * DelayedRewardDistributorConfig
 * This payload is used to determine when a delayed reward should be distributed
 * Which document should be checked for comparison
 * The field to be checked for comparison
 * The value to be checked against
 */

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class DelayedRewardDistributorConfig {
    private LocalDateTime dueDateTime;
    private String document;
    private String documentId;
    private String valueToCheck;
    private String fieldToCheck;
}

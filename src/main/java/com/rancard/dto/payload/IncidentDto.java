/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IncidentDto implements Serializable {

    private String userId;
    private String comment;
    private String type;
    private LocalDateTime time;
}

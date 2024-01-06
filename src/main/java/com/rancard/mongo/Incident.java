/*(C) Gaspay App 2023 */
package com.rancard.mongo;

import com.rancard.dto.payload.IncidentDto;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document
public class Incident extends BaseMongoModel {

    private String userId;
    private String comment;
    private String type;
    private LocalDateTime time;

    public static Incident fromDto(IncidentDto incidentDto) {
        return Incident.builder()
                .userId(incidentDto.getUserId())
                .comment(incidentDto.getComment())
                .type(incidentDto.getType())
                .time(incidentDto.getTime())
                .build();
    }

    public IncidentDto toDto() {
        return IncidentDto.builder().userId(userId).comment(comment).type(type).time(time).build();
    }
}

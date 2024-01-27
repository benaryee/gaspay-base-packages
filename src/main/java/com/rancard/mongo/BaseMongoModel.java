/*(C) Gaspay App 2023-2024 */
package com.rancard.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseMongoModel {

    @Id
    @Field("_id")
    @Indexed
    @JsonIgnore
    protected ObjectId id;

    @JsonIgnore @Version protected Long version;

    @JsonIgnore
    @CreatedDate
    @Field("_created")
    @Indexed
    protected LocalDateTime created;

    @JsonIgnore
    @LastModifiedDate
    @Field("_modified")
    @Indexed
    protected LocalDateTime modified;

    @JsonProperty("id")
    public String getIdString() {
        return (id != null) ? id.toHexString() : null;
    }

    public void setIdString(ObjectId id) {
        this.id = id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}

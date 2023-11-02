package com.rancard.order.model.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@RequiredArgsConstructor
public abstract class BaseMongoModel {
    @Id
    @Field("_id")
    @Indexed
    @JsonIgnore
    protected ObjectId id;

    @JsonIgnore
    @Version
    protected Long version;

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

    @JsonProperty("idString")
    public String getIdString() {
        return (id != null) ? id.toHexString() : null;
    }

}

package com.gaspay.paymentservice.model.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
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

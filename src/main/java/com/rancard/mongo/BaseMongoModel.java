package com.rancard.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rancard.config.CustomDateTimeDeserializer;
import com.rancard.config.CustomDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
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
    @BsonProperty("_id")
    @BsonId
    protected ObjectId id;

    @JsonIgnore
    @Version
    protected Long version;

    @JsonIgnore
    @CreatedDate
    @Field("_created")
    @Indexed
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    protected DateTime created;

    @JsonIgnore
    @LastModifiedDate
    @Field("_modified")
    @Indexed
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    protected DateTime modified;

    @JsonProperty("idString")
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

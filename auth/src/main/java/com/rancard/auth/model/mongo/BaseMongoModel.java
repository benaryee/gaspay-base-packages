package com.rancard.auth.model.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
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

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    protected DateTime created;

    @JsonIgnore
    @LastModifiedDate
    @Field("_modified")
    @Indexed
    protected DateTime modified;

    @JsonProperty("idString")
    public String getIdString() {
        return (id != null) ? id.toHexString() : null;
    }

}

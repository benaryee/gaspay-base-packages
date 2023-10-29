package com.rancard.basepackages.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private int code;

    private String name;

    private String description;

    @CreatedDate
    private DateTime _created;

    @LastModifiedDate
    private DateTime _modified;

    @CreatedBy
    private String createdBy;
}

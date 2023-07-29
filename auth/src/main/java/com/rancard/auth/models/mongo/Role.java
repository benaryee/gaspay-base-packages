package com.rancard.auth.models.mongo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@Document(collection = "roles")

public class Role {

    private int code;

    @NotNull
    private String name;

    private String description;

    @CreatedDate
    private Timestamp _created;

    @LastModifiedDate
    private Timestamp _modified;

    @CreatedBy
    private String createdBy;
}

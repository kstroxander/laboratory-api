package com.i4.laboratory.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;
@Getter
@Builder(toBuilder = true)
@Table(name = BloodTest.TABLE_NAME)
public class BloodTest implements Identifiable<Long> {
    public static final String TABLE_NAME = "blood_tests";
    public static final String TABLE_ALIAS = "bt";
    @Id
    @With
    private final Long id;
    @Column("patient_name")
    private final String patientName;

    @CreatedBy
    @Column("created_by")
    private final String createdBy;
    @CreatedDate
    @Column("created_at")
    private final ZonedDateTime createdAt;
    @LastModifiedBy
    @Column("modified_by")
    private final String modifiedBy;
    @LastModifiedDate
    @Column("modified_at")
    private final ZonedDateTime modifiedAt;
}

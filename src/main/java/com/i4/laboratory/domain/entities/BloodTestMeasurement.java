package com.i4.laboratory.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

import static com.i4.laboratory.domain.entities.BloodTestMeasurement.TABLE_NAME;

@Getter
@Builder(toBuilder = true)
@Table(name =TABLE_NAME)
public class BloodTestMeasurement implements Identifiable<Long> {
    public static final String TABLE_NAME = "blood_test_measurements";
    public static final String TABLE_ALIAS = "btm";
    @Id
    @With
    private final Long id;
    @Column("blood_test_id")
    @With
    private final Long testId;
    @Column("measured_property_id")
    private final MeasurableProperty measuredProperty;
    @Column("measured_value")
    private final Float measuredValue;

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

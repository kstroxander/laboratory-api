package com.i4.laboratory.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.i4.laboratory.domain.entities.BloodTestResult.TABLE_NAME;

@Getter
@Builder(toBuilder = true)
@Table(name = TABLE_NAME)
public class BloodTestResult implements Identifiable<Long> {
    public static final String TABLE_NAME = "blood_test_results";
    public static final String TABLE_ALIAS = "btr";
    @Id
    @With
    private final Long id;
    @Column("blood_test_id")
    @With
    private final Long testId;
    @Column("disease_type_id")
    private final DiseaseType diseaseType;
    @Column("disease_risk_level_id")
    private final DiseaseRiskLevel riskLevel;
}

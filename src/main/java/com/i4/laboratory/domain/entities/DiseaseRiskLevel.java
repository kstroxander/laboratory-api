package com.i4.laboratory.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.i4.laboratory.domain.entities.DiseaseRiskLevel.TABLE_NAME;

@Getter
@Builder(toBuilder = true)
@Table(name = TABLE_NAME)
public class DiseaseRiskLevel implements Identifiable<Long> {
    public static final String TABLE_NAME = "disease_risk_levels";
    public static final String TABLE_ALIAS = "drl";
    @Id
    @With
    private final Long id;
    @Column("name")
    private final String name;
    @Column("description")
    private final String description;
}

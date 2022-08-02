package com.i4.laboratory.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.i4.laboratory.domain.entities.DiseaseTypeRiskConfigurationThreshold.TABLE_NAME;

@ToString
@Getter
@Builder(toBuilder = true)
@Table(name =TABLE_NAME)
public class DiseaseTypeRiskConfigurationThreshold implements Identifiable<Long> {
    public static final String TABLE_NAME = "disease_type_risk_configuration_thresholds";
    public static final String TABLE_ALIAS = "dtrct";
    @Id
    @With
    private final Long id;
    @With
    @Column("disease_type_risk_configuration_id")
    private final Long configurationId;
    @Column("measurable_property_id")
    private final MeasurableProperty property;
    @Column("min_threshold")
    private final Float minThreshold;
    @Column("max_threshold")
    private final Float maxThreshold;
}

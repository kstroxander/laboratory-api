package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.DiseaseTypeRiskConfigurationThreshold;
import com.i4.laboratory.domain.entities.MeasurableProperty;
import com.i4.laboratory.domain.entities.converters.DiseaseTypeRiskConfigurationThresholdConverters;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;

import static com.i4.laboratory.domain.entities.tables.DiseaseTypeRiskConfigurationThresholds.DISEASE_TYPE_RISK_CONFIGURATION_THRESHOLDS;
import static com.i4.laboratory.utils.QueryDSLUtils.*;
import static org.jooq.impl.DSL.param;

public interface CustomDiseaseTypeRiskConfigurationThresholdRepository {
    Flux<DiseaseTypeRiskConfigurationThreshold> findAllByConfigurationId(Long configurationId);

    @RequiredArgsConstructor
    class CustomDiseaseTypeRiskConfigurationThresholdRepositoryImpl implements CustomDiseaseTypeRiskConfigurationThresholdRepository {
        private final DSLContext dsl;
        private final R2dbcEntityTemplate r2dbcTemplate;

        @Override
        public Flux<DiseaseTypeRiskConfigurationThreshold> findAllByConfigurationId(Long configurationId) {
            return r2dbcTemplate.getDatabaseClient().sql(dsl.select(columnArray(
                    columnStream(DISEASE_TYPE_RISK_CONFIGURATION_THRESHOLDS.asterisk()),
                    prefixColumns(DISEASE_TYPE_RISK_CONFIGURATION_THRESHOLDS.measurableProperties(), MeasurableProperty.TABLE_ALIAS)
            ))
                    .from(DISEASE_TYPE_RISK_CONFIGURATION_THRESHOLDS)
                    .where(DISEASE_TYPE_RISK_CONFIGURATION_THRESHOLDS.DISEASE_TYPE_RISK_CONFIGURATION_ID.eq(param("configurationId", Long.class)))
                    .orderBy(DISEASE_TYPE_RISK_CONFIGURATION_THRESHOLDS.MEASURABLE_PROPERTY_ID.asc())
                    .getSQL())
                    .bind("configurationId", configurationId)
                    .map(row -> DiseaseTypeRiskConfigurationThresholdConverters.read(row, null))
                    .all();
        }
    }
}

package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.DiseaseRiskLevel;
import com.i4.laboratory.domain.entities.DiseaseTypeRiskConfiguration;
import com.i4.laboratory.domain.entities.converters.DiseaseTypeRiskConfigurationConverters;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;

import static com.i4.laboratory.domain.entities.tables.DiseaseTypeRiskConfigurations.DISEASE_TYPE_RISK_CONFIGURATIONS;
import static com.i4.laboratory.utils.QueryDSLUtils.*;
import static org.jooq.impl.DSL.param;

public interface CustomDiseaseTypeRiskConfigurationRepository {
    Flux<DiseaseTypeRiskConfiguration> findAllByDiseaseTypeId(Long diseaseTypeId);

    @RequiredArgsConstructor
    class CustomDiseaseTypeRiskConfigurationRepositoryImpl implements CustomDiseaseTypeRiskConfigurationRepository {
        private final DSLContext dsl;
        private final R2dbcEntityTemplate r2dbcTemplate;

        @Override
        public Flux<DiseaseTypeRiskConfiguration> findAllByDiseaseTypeId(Long diseaseTypeId) {
            return r2dbcTemplate.getDatabaseClient().sql(dsl.select(columnArray(
                    columnStream(DISEASE_TYPE_RISK_CONFIGURATIONS.asterisk()),
                    prefixColumns(DISEASE_TYPE_RISK_CONFIGURATIONS.diseaseRiskLevels(), DiseaseRiskLevel.TABLE_ALIAS)
            ))
                    .from(DISEASE_TYPE_RISK_CONFIGURATIONS)
                    .where(DISEASE_TYPE_RISK_CONFIGURATIONS.DISEASE_TYPE_ID.eq(param("diseaseTypeId", Long.class)))
                    .getSQL())
                    .bind("diseaseTypeId", diseaseTypeId)
                    .map(row -> DiseaseTypeRiskConfigurationConverters.read(row, null))
                    .all();
        }
    }
}

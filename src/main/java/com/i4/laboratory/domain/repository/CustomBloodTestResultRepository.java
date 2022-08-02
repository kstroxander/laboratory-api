package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.BloodTestResult;
import com.i4.laboratory.domain.entities.DiseaseRiskLevel;
import com.i4.laboratory.domain.entities.DiseaseType;
import com.i4.laboratory.domain.entities.converters.BloodTestResultConverters;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;

import static com.i4.laboratory.domain.entities.tables.BloodTestResults.BLOOD_TEST_RESULTS;
import static com.i4.laboratory.utils.QueryDSLUtils.*;
import static org.jooq.impl.DSL.param;

public interface CustomBloodTestResultRepository {
    Flux<BloodTestResult> findAllByTestId(Long testId);

    @RequiredArgsConstructor
    class CustomBloodTestResultRepositoryImpl implements CustomBloodTestResultRepository {
        private final DSLContext dsl;
        private final R2dbcEntityTemplate r2dbcTemplate;

        @Override
        public Flux<BloodTestResult> findAllByTestId(Long testId) {
            return r2dbcTemplate.getDatabaseClient().sql(dsl.select(columnArray(
                    columnStream(BLOOD_TEST_RESULTS.asterisk()),
                    prefixColumns(BLOOD_TEST_RESULTS.diseaseTypes(), DiseaseType.TABLE_ALIAS),
                    prefixColumns(BLOOD_TEST_RESULTS.diseaseRiskLevels(), DiseaseRiskLevel.TABLE_ALIAS)
            ))
                    .from(BLOOD_TEST_RESULTS)
                    .where(BLOOD_TEST_RESULTS.BLOOD_TEST_ID.eq(param("testId", Long.class)))
                    .getSQL())
                    .bind("testId", testId)
                    .map(row -> BloodTestResultConverters.read(row, null))
                    .all();
        }
    }
}

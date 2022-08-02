package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.BloodTestMeasurement;
import com.i4.laboratory.domain.entities.MeasurableProperty;
import com.i4.laboratory.domain.entities.converters.BloodTestMeasurementConverters;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;

import static com.i4.laboratory.domain.entities.tables.BloodTestMeasurements.BLOOD_TEST_MEASUREMENTS;
import static com.i4.laboratory.utils.QueryDSLUtils.*;
import static org.jooq.impl.DSL.param;

public interface CustomBloodTestMeasurementRepository {
    Flux<BloodTestMeasurement> findAllByTestId(Long testId);

    @RequiredArgsConstructor
    class CustomBloodTestMeasurementRepositoryImpl implements CustomBloodTestMeasurementRepository {
        private final DSLContext dsl;
        private final R2dbcEntityTemplate r2dbcTemplate;

        @Override
        public Flux<BloodTestMeasurement> findAllByTestId(Long testId) {
            return r2dbcTemplate.getDatabaseClient().sql(dsl.select(columnArray(
                    columnStream(BLOOD_TEST_MEASUREMENTS.asterisk()),
                    prefixColumns(BLOOD_TEST_MEASUREMENTS.measurableProperties(), MeasurableProperty.TABLE_ALIAS)
            ))
                    .from(BLOOD_TEST_MEASUREMENTS)
                    .where(BLOOD_TEST_MEASUREMENTS.BLOOD_TEST_ID.eq(param("testId", Long.class)))
                    .getSQL())
                    .bind("testId", testId)
                    .map(row -> BloodTestMeasurementConverters.read(row, null))
                    .all();
        }
    }
}

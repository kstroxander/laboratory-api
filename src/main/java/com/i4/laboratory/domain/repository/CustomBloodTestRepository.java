package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.dsl.CriteriaSupplier;
import com.i4.laboratory.domain.entities.BloodTest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.i4.laboratory.utils.ValueUtils.defaultValue;
import static org.springframework.data.domain.Pageable.unpaged;
import static org.springframework.data.relational.core.query.Query.query;

public interface CustomBloodTestRepository {
    Flux<BloodTest> findAll(CriteriaSupplier criteriaSupplier);
    Flux<BloodTest> findAll(CriteriaSupplier criteriaSupplier, Pageable pageRequest);
    Mono<Long> count(CriteriaSupplier criteriaSupplier);
    Mono<Long> count(CriteriaSupplier criteriaSupplier, Pageable pageRequest);

    @RequiredArgsConstructor
    class CustomBloodTestRepositoryImpl implements CustomBloodTestRepository {
        private final R2dbcEntityTemplate r2dbcTemplate;

        @Override
        public Flux<BloodTest> findAll(CriteriaSupplier criteriaSupplier) {
            return r2dbcTemplate.select(BloodTest.class)
                    .from(BloodTest.TABLE_NAME)
                    .matching(query(criteriaSupplier.get()))
                    .all();
        }

        @Override
        public Flux<BloodTest> findAll(CriteriaSupplier criteriaSupplier, Pageable pageRequest) {
            return r2dbcTemplate.select(BloodTest.class)
                    .from(BloodTest.TABLE_NAME)
                    .matching(query(criteriaSupplier.get())
                            .with(defaultValue(pageRequest, unpaged())))
                    .all();
        }

        @Override
        public Mono<Long> count(CriteriaSupplier criteriaSupplier) {
            return r2dbcTemplate.count(query(criteriaSupplier.get()), BloodTest.class);
        }

        @Override
        public Mono<Long> count(CriteriaSupplier criteriaSupplier, Pageable pageRequest) {
            return r2dbcTemplate.count(query(criteriaSupplier.get())
                    .with(defaultValue(pageRequest, unpaged())), BloodTest.class);
        }
    }
}

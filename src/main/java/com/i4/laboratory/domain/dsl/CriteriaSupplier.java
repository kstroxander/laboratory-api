package com.i4.laboratory.domain.dsl;

import org.springframework.data.relational.core.query.Criteria;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@FunctionalInterface
public interface CriteriaSupplier {
    Criteria get();

    default CriteriaSupplier and(CriteriaSupplier other) {
        return CriteriaSupplier.composed(this, other, CompositionType.AND);
    }

    enum CompositionType {
        AND {
            @Override
            public Criteria combine(Criteria leftCriteria, Criteria rightCriteria) {
                return Objects.equals(leftCriteria, rightCriteria) ?  leftCriteria : leftCriteria.and(rightCriteria);
            }
        };

        abstract Criteria combine(Criteria leftCriteria, Criteria rightCriteria);
    }

    static CriteriaSupplier composed(CriteriaSupplier left, @Nullable CriteriaSupplier right, CompositionType compositionType) {
        return () -> Stream.of(left, right)
                .filter(Objects::nonNull)
                .map(CriteriaSupplier::get)
                .filter(Objects::nonNull)
                .reduce((accumulated, current) -> isNull(accumulated) || Objects.equals(accumulated, current) ?
                        current : compositionType.combine(accumulated, current))
                .orElse(null);
    }
}

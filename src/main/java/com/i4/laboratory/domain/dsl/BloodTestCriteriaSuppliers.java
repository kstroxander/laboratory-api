package com.i4.laboratory.domain.dsl;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.relational.core.query.Criteria;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.i4.laboratory.domain.dsl.CriteriaSupplierHelper.toContainsText;
import static com.i4.laboratory.utils.DateTimeUtils.*;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.lowerCase;

public class BloodTestCriteriaSuppliers {

    public static CriteriaSupplier byPatientName(String patientName) {
        return () -> Criteria.where("patient_name").like(toContainsText(lowerCase(patientName)));
    }

    public static CriteriaSupplier byCreatedAt(ZonedDateTime createdSince, ZonedDateTime createdUntil) {
        return () -> {
            if(nonNull(createdSince) && nonNull(createdUntil))
                return Criteria.where("created_at").between(
                        atDayStart(createdSince, LocaleContextHolder.getTimeZone().toZoneId()),
                        atMidnight(createdUntil, LocaleContextHolder.getTimeZone().toZoneId())
                );
            if(nonNull(createdSince))
                return Criteria.where("created_at").greaterThanOrEquals(
                        atDayStart(createdSince, LocaleContextHolder.getTimeZone().toZoneId())
                );
            return nonNull(createdUntil) ? Criteria.where("created_at").lessThanOrEquals(
                    atMidnight(createdUntil, LocaleContextHolder.getTimeZone().toZoneId())
            ) : Criteria.empty();
        };
    }


    public static CriteriaSupplier by(Map<String, String> filters) {
        List<CriteriaSupplier> suppliers= new ArrayList<>();

        if(isNotEmpty(filters.get("patientName")))
            suppliers.add(byPatientName(filters.get("patientName")));
        if(isNotEmpty(filters.get("createdSince")) || isNotEmpty(filters.get("createdUntil")))
            suppliers.add(byCreatedAt(parseOrNull(filters.get("createdSince")), parseOrNull(filters.get("createdUntil"))));

        return suppliers.stream()
                .filter(Objects::nonNull)
                .reduce((accumulated, current) -> accumulated.and(current))
                .orElse(Criteria::empty);
    }
}

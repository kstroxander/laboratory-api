package com.i4.laboratory.core.dto;

import com.i4.laboratory.domain.entities.BloodTestMeasurement;
import com.i4.laboratory.domain.entities.BloodTestResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BloodTestDTO {
    private final Long id;
    private final String patientName;
    private final String createdBy;
    private final ZonedDateTime createdAt;
    private final String modifiedBy;
    private final ZonedDateTime modifiedAt;

    private final List<BloodTestMeasurement> measurements;
    private final List<BloodTestResult> results;
}

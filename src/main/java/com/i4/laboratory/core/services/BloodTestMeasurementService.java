package com.i4.laboratory.core.services;

import com.i4.laboratory.core.dto.CreateUpdateBloodTestMeasurementDTO;
import com.i4.laboratory.domain.entities.BloodTestMeasurement;
import reactor.core.publisher.Flux;

import java.util.List;

public interface BloodTestMeasurementService {
    Flux<BloodTestMeasurement> create(List<CreateUpdateBloodTestMeasurementDTO> measurements, Long bloodTestId);
}

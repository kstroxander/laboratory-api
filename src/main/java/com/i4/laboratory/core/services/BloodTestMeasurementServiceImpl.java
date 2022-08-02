package com.i4.laboratory.core.services;

import com.i4.laboratory.core.dto.CreateUpdateBloodTestMeasurementDTO;
import com.i4.laboratory.core.mappers.BloodTestMeasurementMapper;
import com.i4.laboratory.domain.entities.BloodTestMeasurement;
import com.i4.laboratory.domain.repository.BloodTestMeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BloodTestMeasurementServiceImpl implements BloodTestMeasurementService {
    private final BloodTestMeasurementRepository measurementRepository;
    private final BloodTestMeasurementMapper measurementMapper;

    @Override
    public Flux<BloodTestMeasurement> create(List<CreateUpdateBloodTestMeasurementDTO> measurements, Long bloodTestId) {
        return Flux.fromIterable(measurements)
                .map(measurementMapper::toEntity)
                .map(measurement -> measurement.withTestId(bloodTestId))
                .flatMap(measurementRepository::save);
    }
}

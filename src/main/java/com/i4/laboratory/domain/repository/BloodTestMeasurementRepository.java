package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.BloodTestMeasurement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BloodTestMeasurementRepository extends ReactiveCrudRepository<BloodTestMeasurement, Long>, CustomBloodTestMeasurementRepository {
}

package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.MeasurableProperty;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MeasurablePropertyRepository extends ReactiveCrudRepository<MeasurableProperty, Long> {
}

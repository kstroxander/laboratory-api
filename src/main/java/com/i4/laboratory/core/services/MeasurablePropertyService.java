package com.i4.laboratory.core.services;

import com.i4.laboratory.domain.entities.MeasurableProperty;
import reactor.core.publisher.Flux;

public interface MeasurablePropertyService {
    Flux<MeasurableProperty> getAll();
}

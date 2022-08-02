package com.i4.laboratory.core.services;

import com.i4.laboratory.domain.entities.MeasurableProperty;
import com.i4.laboratory.domain.repository.MeasurablePropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@Transactional
@RequiredArgsConstructor
class MeasurablePropertyServiceImpl implements MeasurablePropertyService {
    private final MeasurablePropertyRepository propertyRepository;


    public Flux<MeasurableProperty> getAll() {
        return propertyRepository.findAll();
    }
}

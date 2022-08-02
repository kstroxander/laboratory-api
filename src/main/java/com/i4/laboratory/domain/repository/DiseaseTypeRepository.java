package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.DiseaseType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DiseaseTypeRepository extends ReactiveCrudRepository<DiseaseType, Long> {
}

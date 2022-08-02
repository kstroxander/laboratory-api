package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.DiseaseRiskLevel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DiseaseRiskLevelRepository extends ReactiveCrudRepository<DiseaseRiskLevel, Long> {
}

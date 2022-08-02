package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.DiseaseTypeRiskConfigurationThreshold;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DiseaseTypeRiskConfigurationThresholdRepository
        extends ReactiveCrudRepository<DiseaseTypeRiskConfigurationThreshold, Long>,
        CustomDiseaseTypeRiskConfigurationThresholdRepository {
}

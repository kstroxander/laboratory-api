package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.DiseaseTypeRiskConfiguration;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DiseaseTypeRiskConfigurationRepository
        extends ReactiveCrudRepository<DiseaseTypeRiskConfiguration, Long>,
        CustomDiseaseTypeRiskConfigurationRepository {
}

package com.i4.laboratory.core.services;

import com.i4.laboratory.core.dto.CreateUpdateBloodTestDTO;
import com.i4.laboratory.core.dto.ResultGenerationContext;
import com.i4.laboratory.core.dto.ResultGenerationContext.RiskLevelThresholdConfig;
import com.i4.laboratory.domain.entities.BloodTestResult;
import com.i4.laboratory.domain.entities.DiseaseType;
import com.i4.laboratory.domain.repository.BloodTestResultRepository;
import com.i4.laboratory.domain.repository.DiseaseTypeRepository;
import com.i4.laboratory.domain.repository.DiseaseTypeRiskConfigurationRepository;
import com.i4.laboratory.domain.repository.DiseaseTypeRiskConfigurationThresholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class BloodTestResultServiceImpl implements BloodTestResultService {
    private final BloodTestResultRepository resultRepository;
    private final DiseaseTypeRepository diseaseTypeRepository;
    private final DiseaseTypeRiskConfigurationRepository configRepository;
    private final DiseaseTypeRiskConfigurationThresholdRepository thresholdRepository;
    private final BloodTestResultGenerator resultGenerator;

    @Override
    public Flux<BloodTestResult> resolve(CreateUpdateBloodTestDTO inputDto) {
        return diseaseTypeRepository.findAll()
                .flatMap(diseaseType -> buildRisLevelThresholdConfigs(diseaseType)
                        .collectList()
                        .map(thresholdConfigs -> new ResultGenerationContext(inputDto.getMeasurements(), diseaseType, thresholdConfigs))
                ).map(resultGenerator::generate);
    }

    @Override
    public Flux<BloodTestResult> resolveAndCreate(CreateUpdateBloodTestDTO inputDto, Long bloodTestId) {
        return resolve(inputDto)
                .map(result -> result.withTestId(bloodTestId))
                .flatMap(resultRepository::save);
    }

    private Flux<RiskLevelThresholdConfig> buildRisLevelThresholdConfigs(DiseaseType diseaseType) {
        return configRepository.findAllByDiseaseTypeId(diseaseType.getId())
                .flatMap(config -> thresholdRepository.findAllByConfigurationId(config.getId())
                        .collectList()
                        .zipWith(Mono.justOrEmpty(config.getRiskLevel()), (thresholds, risk) -> new RiskLevelThresholdConfig(risk, thresholds))

                ).filter(thresholdConfig -> !thresholdConfig.thresholdConfigs.isEmpty());

    }

}

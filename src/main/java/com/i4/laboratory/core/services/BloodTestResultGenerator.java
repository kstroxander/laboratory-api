package com.i4.laboratory.core.services;

import com.i4.laboratory.core.dto.ResultGenerationContext;
import com.i4.laboratory.domain.entities.BloodTestResult;
import com.i4.laboratory.domain.entities.DiseaseTypeRiskConfigurationThreshold;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.util.Objects.isNull;

@Component
public class BloodTestResultGenerator {
    public BloodTestResult generate(ResultGenerationContext context) {
        return context.riskLevelConfigs.stream()
                .filter(riskLevelConfig ->
                        riskLevelConfig.thresholdConfigs
                                .stream()
                                .allMatch(thresholdConfig -> evaluateThresholdsMatch(thresholdConfig, context)))
                .findFirst()
                .map(riskLevelConfig ->
                        BloodTestResult.builder()
                                .diseaseType(context.diseaseType)
                                .riskLevel(riskLevelConfig.riskLevel)
                                .build())
                .orElse(BloodTestResult.builder()
                        .diseaseType(context.diseaseType)
                        .build());
    }

    private boolean evaluateThresholdsMatch(DiseaseTypeRiskConfigurationThreshold thresholdConfig, ResultGenerationContext context) {
            return context.measurements.stream().
                    filter(measurement -> measurement.getMeasuredPropertyId().equals(thresholdConfig.getProperty().getId()))
                    .findFirst()
                    .filter(measurement ->
                                    (isNull(thresholdConfig.getMinThreshold()) || measurement.getMeasuredValue() >= thresholdConfig.getMinThreshold()) &&
                                            (isNull(thresholdConfig.getMaxThreshold()) || measurement.getMeasuredValue() <= thresholdConfig.getMaxThreshold()))
                    .map(Objects::nonNull)
                    .orElse(Boolean.FALSE);
    }
}

package com.i4.laboratory.core.dto;

import com.i4.laboratory.domain.entities.DiseaseRiskLevel;
import com.i4.laboratory.domain.entities.DiseaseType;
import com.i4.laboratory.domain.entities.DiseaseTypeRiskConfigurationThreshold;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ResultGenerationContext {
    public final List<CreateUpdateBloodTestMeasurementDTO> measurements;
    public final DiseaseType diseaseType;
    public List<RiskLevelThresholdConfig> riskLevelConfigs;


    @AllArgsConstructor
    public static class RiskLevelThresholdConfig {
        public final DiseaseRiskLevel riskLevel;
        public final List<DiseaseTypeRiskConfigurationThreshold> thresholdConfigs;
    }
}

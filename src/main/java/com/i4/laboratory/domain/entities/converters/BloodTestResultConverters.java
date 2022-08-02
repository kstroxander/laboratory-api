package com.i4.laboratory.domain.entities.converters;

import com.i4.laboratory.domain.entities.BloodTestResult;
import com.i4.laboratory.domain.entities.DiseaseRiskLevel;
import com.i4.laboratory.domain.entities.DiseaseType;
import io.r2dbc.spi.Row;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.stereotype.Component;

import static com.i4.laboratory.domain.entities.converters.ConverterUtils.*;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.springframework.r2dbc.core.Parameter.fromOrEmpty;

public interface BloodTestResultConverters {

    @Component
    @ReadingConverter
    class Read implements DomainEntityConverter<Row, BloodTestResult> {
        @Override
        public BloodTestResult convert(Row source) {
            return read(source, null);
        }
    }

    @Component
    @WritingConverter
    class Write implements DomainEntityConverter<BloodTestResult, OutboundRow>{
        @Override
        public OutboundRow convert(BloodTestResult source) {
            return new OutboundRow()
                    .append("blood_test_id", fromOrEmpty(source.getTestId(), Long.class))
                    .append("disease_type_id", fromOrEmpty(safeGetId(source.getDiseaseType()), Long.class))
                    .append("disease_risk_level_id", fromOrEmpty(safeGetId(source.getRiskLevel()), Long.class))
                    .append("id", fromOrEmpty(source.getId(), Long.class));
        }
    }

    static BloodTestResult read(Row row, String aliasPrefix) {
        AliasPreFixer preFixer = preFixer(defaultString(aliasPrefix));
        if(!row.getMetadata().contains(preFixer.prefix("id"))) return null;

        return BloodTestResult.builder()
                .id(safeGet(preFixer.prefix("id"), row, Long.class))
                .testId(safeGet(preFixer.prefix("blood_test_id"), row, Long.class))
                .diseaseType(DiseaseTypeConverters.read(row, preFixer.prefix(DiseaseType.TABLE_ALIAS)))
                .riskLevel(DiseaseRiskLevelConverters.read(row, preFixer.prefix(DiseaseRiskLevel.TABLE_ALIAS)))
                .build();
    }
}

package com.i4.laboratory.domain.entities.converters;

import com.i4.laboratory.domain.entities.DiseaseRiskLevel;
import com.i4.laboratory.domain.entities.DiseaseType;
import com.i4.laboratory.domain.entities.DiseaseTypeRiskConfiguration;
import io.r2dbc.spi.Row;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.stereotype.Component;

import static com.i4.laboratory.domain.entities.converters.ConverterUtils.*;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.springframework.r2dbc.core.Parameter.fromOrEmpty;

public interface DiseaseTypeRiskConfigurationConverters {

    @Component
    @ReadingConverter
    class Read implements DomainEntityConverter<Row, DiseaseTypeRiskConfiguration> {
        @Override
        public DiseaseTypeRiskConfiguration convert(Row source) {
            return read(source, null);
        }
    }

    @Component
    @WritingConverter
    class Write implements DomainEntityConverter<DiseaseTypeRiskConfiguration, OutboundRow>{
        @Override
        public OutboundRow convert(DiseaseTypeRiskConfiguration source) {
            return new OutboundRow()
                    .append("disease_type_id", fromOrEmpty(safeGetId(source.getDiseaseType()), Long.class))
                    .append("disease_risk_level_id", fromOrEmpty(safeGetId(source.getRiskLevel()), Long.class))
                    .append("id", fromOrEmpty(source.getId(), Long.class));
        }
    }

    static DiseaseTypeRiskConfiguration read(Row row, String aliasPrefix) {
        AliasPreFixer preFixer = preFixer(defaultString(aliasPrefix));
        if(!row.getMetadata().contains(preFixer.prefix("id"))) return null;

        return DiseaseTypeRiskConfiguration.builder()
                .id(safeGet(preFixer.prefix("id"), row, Long.class))
                .diseaseType(DiseaseTypeConverters.read(row, preFixer.prefix(DiseaseType.TABLE_ALIAS)))
                .riskLevel(DiseaseRiskLevelConverters.read(row, preFixer.prefix(DiseaseRiskLevel.TABLE_ALIAS)))
                .build();
    }
}

package com.i4.laboratory.domain.entities.converters;

import com.i4.laboratory.domain.entities.DiseaseTypeRiskConfigurationThreshold;
import com.i4.laboratory.domain.entities.MeasurableProperty;
import io.r2dbc.spi.Row;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.stereotype.Component;

import static com.i4.laboratory.domain.entities.converters.ConverterUtils.*;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.springframework.r2dbc.core.Parameter.fromOrEmpty;

public interface DiseaseTypeRiskConfigurationThresholdConverters {

    @Component
    @ReadingConverter
    class Read implements DomainEntityConverter<Row, DiseaseTypeRiskConfigurationThreshold> {
        @Override
        public DiseaseTypeRiskConfigurationThreshold convert(Row source) {
            return read(source, null);
        }
    }

    @Component
    @WritingConverter
    class Write implements DomainEntityConverter<DiseaseTypeRiskConfigurationThreshold, OutboundRow>{
        @Override
        public OutboundRow convert(DiseaseTypeRiskConfigurationThreshold source) {
            return new OutboundRow()
                    .append("disease_type_risk_configuration_id", fromOrEmpty(source.getConfigurationId(), Long.class))
                    .append("min_threshold", fromOrEmpty(source.getMinThreshold(), Float.class))
                    .append("max_threshold", fromOrEmpty(source.getMaxThreshold(), Float.class))
                    .append("measurable_property_id", fromOrEmpty(safeGetId(source.getProperty()), Long.class))
                    .append("id", fromOrEmpty(source.getId(), Long.class));
        }
    }

    static DiseaseTypeRiskConfigurationThreshold read(Row row, String aliasPrefix) {
        AliasPreFixer preFixer = preFixer(defaultString(aliasPrefix));
        if(!row.getMetadata().contains(preFixer.prefix("id"))) return null;

        return DiseaseTypeRiskConfigurationThreshold.builder()
                .id(safeGet(preFixer.prefix("id"), row, Long.class))
                .configurationId(safeGet(preFixer.prefix("disease_type_risk_configuration_id"), row, Long.class))
                .minThreshold(safeGet(preFixer.prefix("min_threshold"), row, Float.class))
                .maxThreshold(safeGet(preFixer.prefix("max_threshold"), row, Float.class))
                .property(MeasurablePropertyConverters.read(row, preFixer.prefix(MeasurableProperty.TABLE_ALIAS)))
                .build();
    }
}

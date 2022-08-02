package com.i4.laboratory.domain.entities.converters;

import com.i4.laboratory.domain.entities.DiseaseRiskLevel;
import com.i4.laboratory.domain.entities.converters.ConverterUtils.AliasPreFixer;
import io.r2dbc.spi.Row;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.stereotype.Component;

import static com.i4.laboratory.domain.entities.converters.ConverterUtils.preFixer;
import static com.i4.laboratory.domain.entities.converters.ConverterUtils.safeGet;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.springframework.r2dbc.core.Parameter.fromOrEmpty;

public interface DiseaseRiskLevelConverters {

    @Component
    @ReadingConverter
    class Read implements DomainEntityConverter<Row, DiseaseRiskLevel> {
        @Override
        public DiseaseRiskLevel convert(Row source) {
            return read(source, null);
        }
    }

    @Component
    @WritingConverter
    class Write implements DomainEntityConverter<DiseaseRiskLevel, OutboundRow>{
        @Override
        public OutboundRow convert(DiseaseRiskLevel source) {
            return new OutboundRow()
                    .append("name", fromOrEmpty(source.getName(), String.class))
                    .append("description", fromOrEmpty(source.getDescription(), String.class))
                    .append("id", fromOrEmpty(source.getId(), Long.class));
        }
    }

    static DiseaseRiskLevel read(Row row, String aliasPrefix) {
        AliasPreFixer preFixer = preFixer(defaultString(aliasPrefix));
        if(!row.getMetadata().contains(preFixer.prefix("id"))) return null;

        return DiseaseRiskLevel.builder()
                .id(safeGet(preFixer.prefix("id"), row, Long.class))
                .name(safeGet(preFixer.prefix("name"), row, String.class))
                .description(safeGet(preFixer.prefix("description"), row, String.class))
                .build();
    }
}

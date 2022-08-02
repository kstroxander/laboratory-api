package com.i4.laboratory.domain.entities.converters;

import com.i4.laboratory.domain.entities.BloodTest;
import com.i4.laboratory.domain.entities.converters.ConverterUtils.AliasPreFixer;
import io.r2dbc.spi.Row;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

import static com.i4.laboratory.domain.entities.converters.ConverterUtils.preFixer;
import static com.i4.laboratory.domain.entities.converters.ConverterUtils.safeGet;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.springframework.r2dbc.core.Parameter.fromOrEmpty;

public interface BloodTestConverters {
    @Component
    @ReadingConverter
    class Read implements DomainEntityConverter<Row, BloodTest> {
        @Override
        public BloodTest convert(Row source) {
            return read(source, null);
        }
    }

    @Component
    @WritingConverter
    class Write implements DomainEntityConverter<BloodTest, OutboundRow>{
        @Override
        public OutboundRow convert(BloodTest source) {
            return new OutboundRow()
                    .append("patient_name", fromOrEmpty(source.getPatientName(), String.class))
                    .append("created_by", fromOrEmpty(source.getCreatedBy(), String.class))
                    .append("created_at", fromOrEmpty(source.getCreatedAt(), ZonedDateTime.class))
                    .append("modified_by", fromOrEmpty(source.getModifiedBy(), String.class))
                    .append("modified_at", fromOrEmpty(source.getModifiedAt(), ZonedDateTime.class))
                    .append("id", fromOrEmpty(source.getId(), Long.class));
        }
    }

    static BloodTest read(Row row, String aliasPrefix) {
        AliasPreFixer preFixer = preFixer(defaultString(aliasPrefix));
        if(!row.getMetadata().contains(preFixer.prefix("id"))) return null;

        return BloodTest.builder()
                .id(safeGet(preFixer.prefix("id"), row, Long.class))
                .patientName(safeGet(preFixer.prefix("patient_name"), row, String.class))
                .createdBy(safeGet(preFixer.prefix("created_by"), row, String.class))
                .createdAt(safeGet(preFixer.prefix("created_at"), row, ZonedDateTime.class))
                .modifiedBy(safeGet(preFixer.prefix("modified_by"), row, String.class))
                .modifiedAt(safeGet(preFixer.prefix("modified_at"), row, ZonedDateTime.class))
                .build();
    }
}

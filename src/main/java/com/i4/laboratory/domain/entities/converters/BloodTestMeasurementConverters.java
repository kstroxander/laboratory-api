package com.i4.laboratory.domain.entities.converters;

import com.i4.laboratory.domain.entities.BloodTestMeasurement;
import com.i4.laboratory.domain.entities.MeasurableProperty;
import io.r2dbc.spi.Row;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

import static com.i4.laboratory.domain.entities.converters.ConverterUtils.*;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.springframework.r2dbc.core.Parameter.fromOrEmpty;

public interface BloodTestMeasurementConverters {

    @Component
    @ReadingConverter
    class Read implements DomainEntityConverter<Row,  BloodTestMeasurement> {
        @Override
        public  BloodTestMeasurement convert(Row source) {
            return read(source, null);
        }
    }

    @Component
    @WritingConverter
    class Write implements DomainEntityConverter< BloodTestMeasurement, OutboundRow>{
        @Override
        public OutboundRow convert( BloodTestMeasurement source) {
            return new OutboundRow()
                    .append("blood_test_id", fromOrEmpty(source.getTestId(), Long.class))
                    .append("measured_property_id", fromOrEmpty(safeGetId(source.getMeasuredProperty()), Long.class))
                    .append("measured_value", fromOrEmpty(source.getMeasuredValue(), Float.class))
                    .append("created_by", fromOrEmpty(source.getCreatedBy(), String.class))
                    .append("created_at", fromOrEmpty(source.getCreatedAt(), ZonedDateTime.class))
                    .append("modified_by", fromOrEmpty(source.getModifiedBy(), String.class))
                    .append("modified_at", fromOrEmpty(source.getModifiedAt(), ZonedDateTime.class))
                    .append("id", fromOrEmpty(source.getId(), Long.class));
        }
    }

    static BloodTestMeasurement read(Row row, String aliasPrefix) {
        AliasPreFixer preFixer = preFixer(defaultString(aliasPrefix));
        if(!row.getMetadata().contains(preFixer.prefix("id"))) return null;

        return  BloodTestMeasurement.builder()
                .id(safeGet(preFixer.prefix("id"), row, Long.class))
                .testId(safeGet(preFixer.prefix("blood_test_id"), row, Long.class))
                .measuredProperty(MeasurablePropertyConverters.read(row, preFixer.prefix(MeasurableProperty.TABLE_ALIAS)))
                .measuredValue(safeGet(preFixer.prefix("measured_value"), row, Float.class))
                .createdBy(safeGet(preFixer.prefix("created_by"), row, String.class))
                .createdAt(safeGet(preFixer.prefix("created_at"), row, ZonedDateTime.class))
                .modifiedBy(safeGet(preFixer.prefix("modified_by"), row, String.class))
                .modifiedAt(safeGet(preFixer.prefix("modified_at"), row, ZonedDateTime.class))
                .build();
    }
}

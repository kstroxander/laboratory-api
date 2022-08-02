package com.i4.laboratory.domain.entities.converters;

import com.i4.laboratory.domain.entities.Identifiable;
import io.r2dbc.spi.Row;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class ConverterUtils {
    static <T> T safeGet(String columnName, Row row, Class<T> valueType) {
        return ofNullable(columnName)
                .filter(StringUtils::isNotEmpty)
                .filter(row.getMetadata()::contains)
                .map(key -> row.get(key, valueType))
                .orElse(null);
    }

    static <T> T safeGetId(Identifiable<T> identifiable) {
        return ofNullable(identifiable)
                .map(Identifiable::getId)
                .orElse(null);
    }

    static AliasPreFixer preFixer(String prefix) {
        return new AliasPreFixer(isNotEmpty(prefix) ? prefix + "_" : prefix);
    }

    @AllArgsConstructor
    static class AliasPreFixer {
        private final String prefix;

        public String prefix(String alias) {
            return prefix + alias;
        }
    }
}

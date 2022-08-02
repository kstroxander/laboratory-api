package com.i4.laboratory.utils;

import org.jooq.QualifiedAsterisk;
import org.jooq.Record;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Table;

import java.util.stream.Stream;

import static java.util.Objects.nonNull;

public interface QueryDSLUtils {
    static Stream<SelectFieldOrAsterisk> prefixColumns(Table<Record> table, String prefix) {
        return table.fieldStream().map(f -> f.as(prefix + "_" + f.getName()));
    }

    @SafeVarargs
    static SelectFieldOrAsterisk[] columnArray(Stream<SelectFieldOrAsterisk>... streams) {
        return Stream.of(streams)
                .reduce((accumulated, current) -> nonNull(accumulated) ? Stream.concat(accumulated, current) : current)
                .orElseGet(Stream::empty)
                .toArray(SelectFieldOrAsterisk[]::new);
    }

    static Stream<SelectFieldOrAsterisk> columnStream(QualifiedAsterisk asteriskFields) {
        return Stream.of(asteriskFields);
    }
}

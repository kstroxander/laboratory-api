package com.i4.laboratory.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.i4.laboratory.domain.entities.MeasurableProperty.TABLE_NAME;

@ToString
@Getter
@Builder(toBuilder = true)
@Table(name = TABLE_NAME)
public class MeasurableProperty implements Identifiable<Long> {
    public static final String TABLE_NAME = "measurable_properties";
    public static final String TABLE_ALIAS = "mp";
    @Id
    @With
    private final Long id;
    @Column("name")
    private final String name;
    @Column("description")
    private final String description;

    @SuppressWarnings("unused")
    public static MeasurableProperty of(Long id) {
        return MeasurableProperty.builder().id(id).build();
    }
}

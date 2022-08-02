package com.i4.laboratory.domain.entities.converters;

import org.springframework.core.convert.converter.Converter;

public interface DomainEntityConverter<I,O> extends Converter<I,O> {
}

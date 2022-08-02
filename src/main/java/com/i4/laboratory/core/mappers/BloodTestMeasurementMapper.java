package com.i4.laboratory.core.mappers;

import com.i4.laboratory.core.dto.CreateUpdateBloodTestMeasurementDTO;
import com.i4.laboratory.domain.entities.BloodTestMeasurement;
import com.i4.laboratory.domain.entities.MeasurableProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(imports = {MeasurableProperty.class})
public interface BloodTestMeasurementMapper {
    @Mappings({
            @Mapping(target = "measuredProperty", expression = "java( MeasurableProperty.of( source.getMeasuredPropertyId() ) )")
    })
    BloodTestMeasurement toEntity(CreateUpdateBloodTestMeasurementDTO source);
}

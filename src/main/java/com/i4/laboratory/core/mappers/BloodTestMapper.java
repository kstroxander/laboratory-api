package com.i4.laboratory.core.mappers;

import com.i4.laboratory.core.dto.BloodTestDTO;
import com.i4.laboratory.core.dto.BloodTestTableItemDTO;
import com.i4.laboratory.core.dto.CreateUpdateBloodTestDTO;
import com.i4.laboratory.domain.entities.BloodTest;
import com.i4.laboratory.domain.entities.BloodTestMeasurement;
import com.i4.laboratory.domain.entities.BloodTestResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface BloodTestMapper {
    BloodTestTableItemDTO toDto(BloodTest bloodTest, List<BloodTestResult> results);
    BloodTestDTO toDto(BloodTest bloodTest, List<BloodTestMeasurement> measurements, List<BloodTestResult> results);
    BloodTest toEntity(CreateUpdateBloodTestDTO source);
}

package com.i4.laboratory.core.services;

import com.i4.laboratory.core.dto.CreateUpdateBloodTestDTO;
import com.i4.laboratory.domain.entities.BloodTestResult;
import reactor.core.publisher.Flux;

public interface BloodTestResultService {
    Flux<BloodTestResult> resolve(CreateUpdateBloodTestDTO inputDto);
    Flux<BloodTestResult> resolveAndCreate(CreateUpdateBloodTestDTO inputDto, Long bloodTestId);
}

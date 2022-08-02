package com.i4.laboratory.core.services;

import com.i4.laboratory.core.dto.BloodTestDTO;
import com.i4.laboratory.core.dto.BloodTestTableItemDTO;
import com.i4.laboratory.core.dto.CreateUpdateBloodTestDTO;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BloodTestService {
    Mono<Page<BloodTestTableItemDTO>> getPage(Map<String, String> filters);
    Mono<BloodTestDTO> getById(Long id);
    Mono<BloodTestDTO> create(CreateUpdateBloodTestDTO inputDto);
}

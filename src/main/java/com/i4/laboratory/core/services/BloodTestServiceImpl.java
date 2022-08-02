package com.i4.laboratory.core.services;

import com.i4.laboratory.core.dto.BloodTestDTO;
import com.i4.laboratory.core.dto.BloodTestTableItemDTO;
import com.i4.laboratory.core.dto.CreateUpdateBloodTestDTO;
import com.i4.laboratory.core.mappers.BloodTestMapper;
import com.i4.laboratory.domain.entities.BloodTest;
import com.i4.laboratory.domain.repository.BloodTestMeasurementRepository;
import com.i4.laboratory.domain.repository.BloodTestRepository;
import com.i4.laboratory.domain.repository.BloodTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

import static com.i4.laboratory.domain.dsl.BloodTestCriteriaSuppliers.by;
import static com.i4.laboratory.utils.PaginationUtils.buildPageRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class BloodTestServiceImpl implements BloodTestService {
    private final BloodTestRepository testRepository;
    private final BloodTestResultRepository resultRepository;
    private final BloodTestMeasurementRepository measurementRepository;

    private final BloodTestMeasurementService measurementService;
    private final BloodTestResultService resultService;

    private final BloodTestMapper testMapper;

    @Override
    public Mono<Page<BloodTestTableItemDTO>> getPage(Map<String, String> filters) {
        Pageable pageRequest = buildPageRequest(filters);
        return testRepository.findAll(by(filters), pageRequest)
                .flatMap(this::addResults)
                .collectList()
                .zipWith(testRepository.count(by(filters)))
                .map(data -> new PageImpl(data.getT1(), pageRequest, data.getT2()));
    }

    @Override
    public Mono<BloodTestDTO> getById(Long id) {
        return Mono.zip(
                testRepository.findById(id),
                measurementRepository.findAllByTestId(id).collectList().defaultIfEmpty(Collections.emptyList()),
                resultRepository.findAllByTestId(id).collectList().defaultIfEmpty(Collections.emptyList())
        ).map(data -> testMapper.toDto(data.getT1(), data.getT2(), data.getT3()));
    }

    @Override
    public Mono<BloodTestDTO> create(CreateUpdateBloodTestDTO inputDto) {
        return testRepository.save(testMapper.toEntity(inputDto))
                .flatMap(test -> Mono.zip(
                        measurementService.create(inputDto.getMeasurements(), test.getId())
                                .thenMany(measurementRepository.findAllByTestId(test.getId())).collectList(),
                        resultService.resolveAndCreate(inputDto, test.getId()).collectList())
                        .map(data -> testMapper.toDto(test, data.getT1(), data.getT2())));
    }



    private Mono<BloodTestTableItemDTO> addResults(BloodTest bloodTest) {
        return resultRepository.findAllByTestId(bloodTest.getId())
                .collectList()
                .map(results -> testMapper.toDto(bloodTest, results));
    }
}

package com.i4.laboratory.api;

import com.i4.laboratory.core.dto.CreateUpdateBloodTestDTO;
import com.i4.laboratory.core.services.BloodTestResultService;
import com.i4.laboratory.domain.entities.BloodTestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blood-test-results")
public class BloodTestResultController {
    final BloodTestResultService testResultService;
    @ResponseBody
    @PostMapping("/resolve")
    Flux<BloodTestResult> resolve(@Valid @RequestBody CreateUpdateBloodTestDTO inputDto) {
        return testResultService.resolve(inputDto);
    }
}

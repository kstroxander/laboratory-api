package com.i4.laboratory.api;

import com.i4.laboratory.core.dto.BloodTestDTO;
import com.i4.laboratory.core.dto.BloodTestTableItemDTO;
import com.i4.laboratory.core.dto.CreateUpdateBloodTestDTO;
import com.i4.laboratory.core.services.BloodTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blood-tests")
public class BloodTestController {
    final BloodTestService testService;

    @ResponseBody
    @GetMapping("/{id}")
    Mono<BloodTestDTO> getById(@PathVariable("id") Long id) {
        return testService.getById(id);
    }

    @ResponseBody
    @GetMapping
    Mono<Page<BloodTestTableItemDTO>> getAll(@RequestParam Map<String, String> filters) {
        return testService.getPage(filters);
    }

    @ResponseBody
    @PostMapping
    Mono<BloodTestDTO> create(@Valid @RequestBody CreateUpdateBloodTestDTO inputDto) {
        return testService.create(inputDto);
    }
}

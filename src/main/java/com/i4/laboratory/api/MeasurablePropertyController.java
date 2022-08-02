package com.i4.laboratory.api;

import com.i4.laboratory.core.services.MeasurablePropertyService;
import com.i4.laboratory.domain.entities.MeasurableProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/measurable-properties")
public class MeasurablePropertyController {
    final MeasurablePropertyService propertyService;

    @ResponseBody
    @GetMapping
    Flux<MeasurableProperty> resolve() {
        return propertyService.getAll();
    }
}

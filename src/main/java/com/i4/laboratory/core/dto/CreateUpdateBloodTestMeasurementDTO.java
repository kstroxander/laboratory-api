package com.i4.laboratory.core.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUpdateBloodTestMeasurementDTO {
    @NotNull(message = "Debes especificar el identificador de cada tipo de analito medido")
    private final Long measuredPropertyId;
    @NotNull(message = "Debes especificar el valor de cada tipo de analito medido")
    @Min(value = 0L, message = "El valor de cada analito no debe ser inferior a {value}")
    @Max(value = 1L, message = "El valor de cada analito no debe ser superior {value}")
    private final Float measuredValue;
}

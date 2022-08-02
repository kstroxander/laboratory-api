package com.i4.laboratory.core.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUpdateBloodTestDTO {
    @NotEmpty(message = "Debes ingresar el nombre del paciente")
    private final String patientName;
    @Valid
    @NotEmpty(message = "Debes ingresar los valores del examen")
    @Size(min = 3, message = "Debes ingresar todos los valores del examen")
    private final List<CreateUpdateBloodTestMeasurementDTO> measurements;
}

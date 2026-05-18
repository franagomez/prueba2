package com.arriendo.ms_sucursales.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank(message = "El código es obligatorio")
    @Size(min = 2, max = 20)
    private String codigo;

    @NotNull(message = "El número de región es obligatorio")
    @Positive(message = "El número de región debe ser positivo")
    private Integer numeroRegion;

    private Boolean activo;

    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaCreacion;
}

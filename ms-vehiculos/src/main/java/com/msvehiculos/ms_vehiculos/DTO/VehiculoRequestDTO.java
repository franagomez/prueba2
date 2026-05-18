package com.msvehiculos.ms_vehiculos.DTO;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoRequestDTO {

    @NotBlank
    @Size(min = 5, max = 10)
    private String patente;

    @NotBlank
    @Size(min = 2, max = 50)
    private String marca;

    @NotBlank
    @Size(min = 2, max = 50)
    private String modelo;

    @NotNull
    @Positive
    private Integer anio;

    @NotNull
    @Positive
    private Double precioArriendoDiario;

    private boolean disponible = true;

    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;

    @NotNull
    private Integer categoriaId;
}

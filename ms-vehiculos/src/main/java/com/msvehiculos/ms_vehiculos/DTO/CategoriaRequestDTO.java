package com.msvehiculos.ms_vehiculos.DTO;


import ch.qos.logback.core.joran.spi.NoAutoStart;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {

    @NotBlank
    @Size(min =3, max= 50)
    private String nombre;

    @NotBlank
    @Size(min =5, max= 50)
    private String descripcion;

    @NotNull
    @PositiveOrZero
    private Integer cantidadVehiculos;

    private boolean activa = true;

    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;

}

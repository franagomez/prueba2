package com.msclientes.ms_clientes.DTO;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DireccionRequestDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String calle;

    @NotBlank
    @Size(min = 1, max = 50)
    private String numero;

    @NotBlank
    @Size(min = 2, max = 100)
    private String comuna;

    @NotBlank
    @Size(min = 2, max = 100)
    private String ciudad;

    @NotNull
    @PositiveOrZero
    private Integer codigoPostal;

    private boolean principal = false;

    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;

    @NotNull
    private Integer clienteId;

}

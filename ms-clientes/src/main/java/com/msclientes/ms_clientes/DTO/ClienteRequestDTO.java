package com.msclientes.ms_clientes.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClienteRequestDTO {

    @Schema(
            description = "RUN del cliente",
            example = "20456789-3"
    )
    @NotBlank
    @Size(min = 9, max = 13)
    private String run;

    @Schema(
            description = "Nombre del cliente",
            example = "Juan"
    )
    @NotBlank
    @Size(min = 2, max = 50)
    private String nombre;

    @Schema(
            description = "Apellido del cliente",
            example = "Pérez"
    )
    @NotBlank
    @Size(min = 2, max = 50)
    private String apellido;

    @Schema(
            description = "Correo electrónico del cliente",
            example = "juan.perez@gmail.com"
    )
    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @Schema(
            description = "Teléfono de contacto",
            example = "+56912345678"
    )
    @NotBlank
    @Size(min = 10, max = 15)
    private String telefono;


    @Schema(
            description = "Puntos acumulados del cliente",
            example = "150"
    )
    // @positiveorzero en vez de solo @Positive
    // ya que un cliente al ser nuevo comienza con 0 puntos
    @PositiveOrZero
    private Integer puntosCliente;


    @Schema(
            description = "Indica si el cliente se encuentra activo",
            example = "true"
    )
    private boolean activo = true;


    @Schema(
            description = "Fecha de registro del cliente",
            example = "2026-03-01"
    )
    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;
}

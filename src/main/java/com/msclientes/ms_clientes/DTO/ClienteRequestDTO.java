package com.msclientes.ms_clientes.DTO;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClienteRequestDTO {

    @NotBlank
    @Size(min = 9, max = 13)
    private String run;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nombre;

    @NotBlank
    @Size(min = 2, max = 50)
    private String apellido;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    // es un string porque el teléfono a veces lleva espacios, signos como "+" o "-".
    @NotBlank
    @Size(min = 10, max = 15)
    private String telefono;


    // @positiveorzero en vez de solo @Positive
    // ya que un cliente al ser nuevo comienza con 0 puntos
    @PositiveOrZero
    private Integer puntosCliente;

    private boolean activo = true;

    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;
}

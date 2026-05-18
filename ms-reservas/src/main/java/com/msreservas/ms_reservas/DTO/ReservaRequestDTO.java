package com.msreservas.ms_reservas.DTO;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    private String nombreCliente;

    @NotNull
    @Positive
    private Integer vehiculoId;

    @NotNull
    @Positive
    private Integer cantidadDias;

    @NotNull
    @Positive
    private Double totalReserva;

    private boolean activa = true;

    @NotNull
    @PastOrPresent
    private LocalDate fechaReserva;

    @NotNull
    private Integer estadoReservaId;
}

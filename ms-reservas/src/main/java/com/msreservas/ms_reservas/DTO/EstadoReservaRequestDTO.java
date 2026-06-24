package com.msreservas.ms_reservas.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para registrar o actualizar estados de reserva")
public class EstadoReservaRequestDTO {

    @Schema(description = "Nombre del estado", example = "Confirmada")
    @NotBlank
    @Size(min = 3, max = 50)
    private String nombre;

    @Schema(description = "Descripción del estado", example = "Reserva confirmada por el sistema")
    @NotBlank
    @Size(min = 5, max = 200)
    private String descripcion;

    @Schema(description = "Prioridad del estado", example = "1")
    @NotNull
    @PositiveOrZero
    private Integer prioridad;

    @Schema(description = "Indica si el estado está activo", example = "true")
    private boolean activo = true;

    @Schema(description = "Fecha de creación", example = "2026-06-20")
    @NotNull
    @PastOrPresent
    private LocalDate fechaCreacion;





}

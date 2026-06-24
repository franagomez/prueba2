package com.msclientes.ms_clientes.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClienteDTO {
    @Schema(description = "Identificador único del cliente", example = "1")
    private Integer id;
    @Schema(description = "RUN del cliente", example = "20456789-3")
    private String run;
    @Schema(description = "Nombre del cliente", example = "Juan")
    private String nombre;
    @Schema(description = "Apellido del cliente", example = "Pérez")
    private String apellido;
    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@gmail.com")
    private String email;
    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    private String telefono;
    @Schema(description = "Cantidad de puntos acumulados", example = "150")
    private Integer puntosCliente;
    @Schema(description = "Estado del cliente", example = "true")
    private boolean activo;
    @Schema(description = "Fecha de registro del cliente", example = "2026-06-19")
    private LocalDate fechaRegistro;

}

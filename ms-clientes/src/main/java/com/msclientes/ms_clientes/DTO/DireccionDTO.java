package com.msclientes.ms_clientes.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DireccionDTO {

    @Schema(
            description = "Identificador único de la dirección",
            example = "1"
    )
    private Integer id;
    @Schema(
            description = "Nombre de la calle",
            example = "Av. Providencia"
    )
    private String calle;
    @Schema(
            description = "Número de la dirección",
            example = "1234"
    )
    private String numero;
    @Schema(
            description = "Comuna de la dirección",
            example = "Providencia"
    )
    private String comuna;
    @Schema(
            description = "Ciudad de la dirección",
            example = "Santiago"
    )
    private String ciudad;
    @Schema(
            description = "Código postal de la dirección",
            example = "7500000"
    )
    private Integer codigoPostal;
    @Schema(
            description = "Indica si es la dirección principal del cliente",
            example = "true"
    )
    private boolean principal;
    @Schema(
            description = "Fecha de registro de la dirección",
            example = "2026-03-01"
    )
    private LocalDate fechaRegistro;
    @Schema(
            description = "Identificador del cliente asociado",
            example = "1"
    )
    private Integer clienteId;
    // clienteID es importante ya que nos permite verificar a que cliente corresponde cada direccion
    // sin tener que devolver el objeto completo
}

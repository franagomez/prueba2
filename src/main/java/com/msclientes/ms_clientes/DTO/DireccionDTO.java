package com.msclientes.ms_clientes.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DireccionDTO {

    private Integer id;
    private String calle;
    private String numero;
    private String comuna;
    private String ciudad;
    private Integer codigoPostal;
    private boolean principal;
    private LocalDate fechaRegistro;
    private Integer clienteId;
    // clienteID es importante ya que nos permite verificar a que cliente corresponde cada direccion
    // sin tener que devolver el objeto completo
}

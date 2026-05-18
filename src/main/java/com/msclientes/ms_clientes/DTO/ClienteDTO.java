package com.msclientes.ms_clientes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClienteDTO {
    private Integer id;
    private String run;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Integer puntosCliente;
    private boolean activo;
    private LocalDate fechaRegistro;

}

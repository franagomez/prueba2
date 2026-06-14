package com.msreservas.ms_reservas.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoReservaDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer prioridad;
    private boolean activo;
    private LocalDate fechaCreacion;
}

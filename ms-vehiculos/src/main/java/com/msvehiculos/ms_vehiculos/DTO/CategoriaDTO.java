package com.msvehiculos.ms_vehiculos.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer cantidadVehiculos;
    private boolean activa = true;
    private LocalDate fechaCreacion;

}

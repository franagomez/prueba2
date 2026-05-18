package com.arriendo.ms_sucursales.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalResponseDTO {

    private Integer id;
    private String nombre;
    private String direccion;
    private Integer capacidadVehiculos;
    private Boolean operativa;
    private LocalDate fechaApertura;
    private Integer regionId;
    private String nombreRegion;
}

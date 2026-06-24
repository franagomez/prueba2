package com.arriendo.ms_sucursales.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponseDTO {

    private Integer id;
    private String nombre;
    private String codigo;
    private Integer numeroRegion;
    private Boolean activo;
    private LocalDate fechaCreacion;
}

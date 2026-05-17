package com.msvehiculos.ms_vehiculos.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {

    private Integer id;
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private Double precioArriendoDiario;
    private boolean disponible;
    private LocalDate fechaRegistro;


    //categoriaId nos permite saber a que categoria pertenece el vehiculo
    // sin necesidad de devolver el objeto completo
    private Integer categoriaId;


}

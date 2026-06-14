package com.msreservas.ms_reservas.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Integer id;
    private String nombreCliente;
    private Integer vehiculoId;
    private Integer cantidadDias;
    private Double totalReserva;
    private boolean activa;
    private LocalDate fechaReserva;
    private Integer estadoReservaId;

}

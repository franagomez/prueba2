package com.msreservas.ms_reservas.Mapper;

import com.msreservas.ms_reservas.DTO.ReservaDTO;
import com.msreservas.ms_reservas.DTO.ReservaRequestDTO;
import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public ReservaDTO toReservaDTO(Reserva reserva){

        ReservaDTO dto = new ReservaDTO();

        dto.setId(reserva.getId());
        dto.setClienteId(reserva.getClienteId());
        dto.setNombreCliente(reserva.getNombreCliente());
        dto.setVehiculoId(reserva.getVehiculoId());
        dto.setCantidadDias(reserva.getCantidadDias());
        dto.setTotalReserva(reserva.getTotalReserva());
        dto.setActiva(reserva.isActiva());
        dto.setFechaReserva(reserva.getFechaReserva());

        if (reserva.getEstadoReserva() != null) {
            dto.setEstadoReservaId(reserva.getEstadoReserva().getId());
        }

        return dto;
    }

    public Reserva toEntity(ReservaRequestDTO dto){
        Reserva reserva = new Reserva();

        reserva.setClienteId(dto.getClienteId());
        reserva.setNombreCliente(dto.getNombreCliente());
        reserva.setVehiculoId(dto.getVehiculoId());
        reserva.setCantidadDias(dto.getCantidadDias());
        reserva.setTotalReserva(dto.getTotalReserva());
        reserva.setActiva(dto.isActiva());
        reserva.setFechaReserva(dto.getFechaReserva());

        EstadoReserva estado = new EstadoReserva();
        estado.setId(dto.getEstadoReservaId());

        reserva.setEstadoReserva(estado);

        return reserva;
    }




}

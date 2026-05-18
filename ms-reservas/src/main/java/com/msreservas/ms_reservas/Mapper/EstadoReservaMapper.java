package com.msreservas.ms_reservas.Mapper;


import com.msreservas.ms_reservas.DTO.EstadoReservaDTO;
import com.msreservas.ms_reservas.DTO.EstadoReservaRequestDTO;
import com.msreservas.ms_reservas.Model.EstadoReserva;
import org.springframework.stereotype.Component;

@Component
public class EstadoReservaMapper {
    public EstadoReservaDTO toDTO(EstadoReserva estado){

        EstadoReservaDTO dto = new EstadoReservaDTO();

        dto.setId(estado.getId());
        dto.setNombre(estado.getNombre());
        dto.setDescripcion(estado.getDescripcion());
        dto.setPrioridad(estado.getPrioridad());
        dto.setActivo(estado.isActivo());
        dto.setFechaCreacion(estado.getFechaCreacion());

        return dto;
    }

    public EstadoReserva toEntity(EstadoReservaRequestDTO dto){

        EstadoReserva estado = new EstadoReserva();

        estado.setNombre(dto.getNombre());
        estado.setDescripcion(dto.getDescripcion());
        estado.setPrioridad(dto.getPrioridad());
        estado.setActivo(dto.isActivo());
        estado.setFechaCreacion(dto.getFechaCreacion());

        return estado;
    }
}

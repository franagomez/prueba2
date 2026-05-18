package com.arriendo.ms_empleados.mapper;

import com.arriendo.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendo.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendo.ms_empleados.model.Empleado;

public class EmpleadoMapper {

    public static Empleado toEntity(EmpleadoRequestDTO dto) {

        Empleado empleado = new Empleado();

        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setEmail(dto.getEmail());
        empleado.setSueldo(dto.getSueldo());
        empleado.setActivo(dto.getActivo());
        empleado.setCargo(dto.getCargo());
        empleado.setFechaContratacion(dto.getFechaContratacion());

        return empleado;
    }

    public static EmpleadoResponseDTO toDTO(Empleado empleado) {

        return new EmpleadoResponseDTO(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getEmail(),
                empleado.getSueldo(),
                empleado.getActivo(),
                empleado.getCargo(),
                empleado.getFechaContratacion()
        );
    }
}

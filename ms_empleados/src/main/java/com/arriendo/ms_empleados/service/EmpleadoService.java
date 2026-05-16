package com.arriendo.ms_empleados.service;

import com.arriendo.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendo.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendo.ms_empleados.mapper.EmpleadoMapper;
import com.arriendo.ms_empleados.model.Empleado;
import com.arriendo.ms_empleados.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    public EmpleadoResponseDTO guardar(EmpleadoRequestDTO dto) {

        Empleado empleado = EmpleadoMapper.toEntity(dto);

        Empleado empleadoGuardado = empleadoRepository.save(empleado);

        return EmpleadoMapper.toDTO(empleadoGuardado);
    }
}
package com.arriendo.ms_empleados.service;

import com.arriendo.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendo.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendo.ms_empleados.exception.ResourceNotFoundException;
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

    public Empleado obtenerPorId(Long id) {

        return empleadoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Empleado no encontrado"));
    }

    public EmpleadoResponseDTO guardar(EmpleadoRequestDTO dto) {

        Empleado empleado = EmpleadoMapper.toEntity(dto);

        Empleado empleadoGuardado = empleadoRepository.save(empleado);

        return EmpleadoMapper.toDTO(empleadoGuardado);
    }

    public Empleado actualizar(Long id, EmpleadoRequestDTO dto) {

        Empleado empleado = obtenerPorId(id);

        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setEmail(dto.getEmail());
        empleado.setSueldo(dto.getSueldo());
        empleado.setActivo(dto.getActivo());
        empleado.setCargo(dto.getCargo());
        empleado.setFechaContratacion(dto.getFechaContratacion());

        return empleadoRepository.save(empleado);
    }

    public void eliminar(Long id) {

        Empleado empleado = obtenerPorId(id);

        empleadoRepository.delete(empleado);
    }
}
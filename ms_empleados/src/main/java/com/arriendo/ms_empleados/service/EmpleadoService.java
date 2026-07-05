package com.arriendo.ms_empleados.service;

import com.arriendo.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendo.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendo.ms_empleados.exception.ResourceNotFoundException;
import com.arriendo.ms_empleados.mapper.EmpleadoMapper;
import com.arriendo.ms_empleados.model.Empleado;
import com.arriendo.ms_empleados.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private static final Logger log =
            LoggerFactory.getLogger(EmpleadoService.class);

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> obtenerTodos() {
        log.info("Listando todos los empleados");
        return empleadoRepository.findAll();
    }

    public List<Empleado> buscarActivosPorAnio(Integer anio) {
        log.info("Buscando empleados activos contratados en el año {}", anio);
        return empleadoRepository.buscarActivosPorAnio(anio);
    }

    public Empleado obtenerPorId(Long id) {
        log.info("Buscando empleado con id {}", id);

        return empleadoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Empleado no encontrado"));
    }

    public EmpleadoResponseDTO guardar(EmpleadoRequestDTO dto) {
        log.info("Guardando nuevo empleado");

        Empleado empleado = EmpleadoMapper.toEntity(dto);
        Empleado empleadoGuardado = empleadoRepository.save(empleado);

        return EmpleadoMapper.toDTO(empleadoGuardado);
    }

    public Empleado actualizar(Long id, EmpleadoRequestDTO dto) {
        log.info("Actualizando empleado con id {}", id);

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
        log.info("Eliminando empleado con id {}", id);

        Empleado empleado = obtenerPorId(id);
        empleadoRepository.delete(empleado);
    }
}
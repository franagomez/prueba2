package com.arriendo.ms_empleados.service;

import com.arriendo.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendo.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendo.ms_empleados.exception.ResourceNotFoundException;
import com.arriendo.ms_empleados.model.Empleado;
import com.arriendo.ms_empleados.repository.EmpleadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void obtenerTodos_CuandoExistenEmpleados_DebeRetornarLista() {
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("Francisca");
        empleado.setApellido("Gomez");
        empleado.setEmail("francisca@test.cl");
        empleado.setSueldo(850000.0);
        empleado.setActivo(true);
        empleado.setCargo("Soporte");
        empleado.setFechaContratacion(LocalDate.of(2026, 6, 19));

        when(empleadoRepository.findAll()).thenReturn(List.of(empleado));

        List<Empleado> resultado = empleadoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Francisca", resultado.get(0).getNombre());

        verify(empleadoRepository, times(1)).findAll();
    }

    @Test
    void buscarActivosPorAnio_CuandoExistenEmpleados_DebeRetornarLista() {
        Integer anio = 2026;

        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("Empleado Activo");
        empleado.setActivo(true);
        empleado.setFechaContratacion(LocalDate.of(2026, 3, 10));

        when(empleadoRepository.buscarActivosPorAnio(anio))
                .thenReturn(List.of(empleado));

        List<Empleado> resultado = empleadoService.buscarActivosPorAnio(anio);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getActivo());

        verify(empleadoRepository, times(1)).buscarActivosPorAnio(anio);
    }

    @Test
    void obtenerPorId_CuandoEmpleadoExiste_DebeRetornarEmpleado() {
        Long id = 1L;

        Empleado empleado = new Empleado();
        empleado.setId(id);
        empleado.setNombre("Francisca");

        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleado));

        Empleado resultado = empleadoService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Francisca", resultado.getNombre());

        verify(empleadoRepository, times(1)).findById(id);
    }

    @Test
    void obtenerPorId_CuandoEmpleadoNoExiste_DebeLanzarResourceNotFoundException() {
        Long id = 99L;

        when(empleadoRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> empleadoService.obtenerPorId(id)
        );

        assertEquals("Empleado no encontrado", exception.getMessage());

        verify(empleadoRepository, times(1)).findById(id);
    }

    @Test
    void guardar_CuandoDatosValidos_DebeRetornarEmpleadoResponseDTO() {
        EmpleadoRequestDTO dto = new EmpleadoRequestDTO();
        dto.setNombre("Francisca");
        dto.setApellido("Gomez");
        dto.setEmail("francisca@test.cl");
        dto.setSueldo(850000.0);
        dto.setActivo(true);
        dto.setCargo("Soporte");
        dto.setFechaContratacion(LocalDate.of(2026, 6, 19));

        Empleado empleadoGuardado = new Empleado();
        empleadoGuardado.setId(1L);
        empleadoGuardado.setNombre("Francisca");
        empleadoGuardado.setApellido("Gomez");
        empleadoGuardado.setEmail("francisca@test.cl");
        empleadoGuardado.setSueldo(850000.0);
        empleadoGuardado.setActivo(true);
        empleadoGuardado.setCargo("Soporte");
        empleadoGuardado.setFechaContratacion(LocalDate.of(2026, 6, 19));

        when(empleadoRepository.save(any(Empleado.class)))
                .thenReturn(empleadoGuardado);

        EmpleadoResponseDTO resultado = empleadoService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Francisca", resultado.getNombre());
        assertEquals("Soporte", resultado.getCargo());

        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }

    @Test
    void actualizar_CuandoEmpleadoExiste_DebeActualizarEmpleado() {
        Long id = 1L;

        EmpleadoRequestDTO dto = new EmpleadoRequestDTO();
        dto.setNombre("Francisca Actualizada");
        dto.setApellido("Gomez");
        dto.setEmail("actualizada@test.cl");
        dto.setSueldo(900000.0);
        dto.setActivo(true);
        dto.setCargo("Soporte Nivel 2");
        dto.setFechaContratacion(LocalDate.of(2026, 6, 20));

        Empleado empleadoExistente = new Empleado();
        empleadoExistente.setId(id);
        empleadoExistente.setNombre("Francisca");
        empleadoExistente.setApellido("Gomez");
        empleadoExistente.setEmail("francisca@test.cl");
        empleadoExistente.setSueldo(850000.0);
        empleadoExistente.setActivo(false);
        empleadoExistente.setCargo("Soporte");
        empleadoExistente.setFechaContratacion(LocalDate.of(2026, 6, 19));

        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleadoExistente));
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoExistente);

        Empleado resultado = empleadoService.actualizar(id, dto);

        assertNotNull(resultado);
        assertEquals("Francisca Actualizada", resultado.getNombre());
        assertEquals("actualizada@test.cl", resultado.getEmail());
        assertEquals(900000.0, resultado.getSueldo());
        assertTrue(resultado.getActivo());
        assertEquals("Soporte Nivel 2", resultado.getCargo());

        verify(empleadoRepository, times(1)).findById(id);
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }

    @Test
    void eliminar_CuandoEmpleadoExiste_DebeEliminarEmpleado() {
        Long id = 1L;

        Empleado empleado = new Empleado();
        empleado.setId(id);
        empleado.setNombre("Empleado a eliminar");

        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleado));

        empleadoService.eliminar(id);

        verify(empleadoRepository, times(1)).findById(id);
        verify(empleadoRepository, times(1)).delete(empleado);
    }
}

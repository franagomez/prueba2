package com.msreservas.ms_reservas.Service;

import com.msreservas.ms_reservas.DTO.EstadoReservaDTO;
import com.msreservas.ms_reservas.DTO.EstadoReservaRequestDTO;
import com.msreservas.ms_reservas.Exception.ResourceNotFoundException;
import com.msreservas.ms_reservas.Mapper.EstadoReservaMapper;
import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Repository.EstadoReservaRepository;
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
class EstadoReservaServiceTest {

    @Mock
    private EstadoReservaRepository estadoReservaRepository;

    @Mock
    private EstadoReservaMapper estadoReservaMapper;

    @InjectMocks
    private EstadoReservaService estadoReservaService;

    @Test
    void findAll_CuandoExistenEstados_DebeRetornarListaDTO() {

        EstadoReserva estado = new EstadoReserva();
        estado.setId(1);
        estado.setNombre("Confirmada");

        EstadoReservaDTO dto = new EstadoReservaDTO();
        dto.setId(1);
        dto.setNombre("Confirmada");

        when(estadoReservaRepository.findAll()).thenReturn(List.of(estado));
        when(estadoReservaMapper.toDTO(estado)).thenReturn(dto);

        List<EstadoReservaDTO> resultado = estadoReservaService.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Confirmada", resultado.get(0).getNombre());

        verify(estadoReservaRepository).findAll();
    }

    @Test
    void findById_CuandoExiste_DebeRetornarDTO() {

        EstadoReserva estado = new EstadoReserva();
        estado.setId(1);
        estado.setNombre("Pendiente");

        EstadoReservaDTO dto = new EstadoReservaDTO();
        dto.setId(1);
        dto.setNombre("Pendiente");

        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estado));
        when(estadoReservaMapper.toDTO(estado)).thenReturn(dto);

        EstadoReservaDTO resultado = estadoReservaService.findById(1);

        assertEquals("Pendiente", resultado.getNombre());

        verify(estadoReservaRepository).findById(1);
    }

    @Test
    void findById_CuandoNoExiste_DebeLanzarExcepcion() {

        when(estadoReservaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> estadoReservaService.findById(99));

        verify(estadoReservaRepository).findById(99);
    }

    @Test
    void save_CuandoDatosValidos_DebeGuardarEstado() {

        EstadoReservaRequestDTO request = new EstadoReservaRequestDTO();
        request.setNombre("Confirmada");
        request.setDescripcion("Reserva confirmada");
        request.setPrioridad(1);
        request.setActivo(true);
        request.setFechaCreacion(LocalDate.now());

        EstadoReserva estado = new EstadoReserva();
        estado.setNombre("Confirmada");

        EstadoReserva guardado = new EstadoReserva();
        guardado.setId(1);
        guardado.setNombre("Confirmada");

        EstadoReservaDTO dto = new EstadoReservaDTO();
        dto.setId(1);
        dto.setNombre("Confirmada");

        when(estadoReservaMapper.toEntity(request)).thenReturn(estado);
        when(estadoReservaRepository.save(estado)).thenReturn(guardado);
        when(estadoReservaMapper.toDTO(guardado)).thenReturn(dto);

        EstadoReservaDTO resultado = estadoReservaService.save(request);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());

        verify(estadoReservaRepository).save(estado);
    }

    @Test
    void update_CuandoExiste_DebeActualizarEstado() {

        EstadoReservaRequestDTO request = new EstadoReservaRequestDTO();
        request.setNombre("Cancelada");
        request.setDescripcion("Cancelada");
        request.setPrioridad(2);
        request.setActivo(false);
        request.setFechaCreacion(LocalDate.now());

        EstadoReserva estado = new EstadoReserva();
        estado.setId(1);
        estado.setNombre("Pendiente");

        EstadoReserva actualizado = new EstadoReserva();
        actualizado.setId(1);
        actualizado.setNombre("Cancelada");

        EstadoReservaDTO dto = new EstadoReservaDTO();
        dto.setId(1);
        dto.setNombre("Cancelada");

        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estado));
        when(estadoReservaRepository.save(estado)).thenReturn(actualizado);
        when(estadoReservaMapper.toDTO(actualizado)).thenReturn(dto);

        EstadoReservaDTO resultado = estadoReservaService.update(1, request);

        assertEquals("Cancelada", resultado.getNombre());

        verify(estadoReservaRepository).save(estado);
    }

    @Test
    void update_CuandoNoExiste_DebeLanzarExcepcion() {

        EstadoReservaRequestDTO request = new EstadoReservaRequestDTO();

        when(estadoReservaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> estadoReservaService.update(99, request));

        verify(estadoReservaRepository).findById(99);
    }

    @Test
    void delete_CuandoExiste_DebeEliminar() {

        when(estadoReservaRepository.existsById(1)).thenReturn(true);

        boolean resultado = estadoReservaService.delete(1);

        assertTrue(resultado);

        verify(estadoReservaRepository).deleteById(1);
    }

    @Test
    void delete_CuandoNoExiste_DebeRetornarFalse() {

        when(estadoReservaRepository.existsById(99)).thenReturn(false);

        boolean resultado = estadoReservaService.delete(99);

        assertFalse(resultado);

        verify(estadoReservaRepository, never()).deleteById(anyInt());
    }

}
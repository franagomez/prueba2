package com.msreservas.ms_reservas.Service;

import com.msreservas.ms_reservas.DTO.ReservaDTO;
import com.msreservas.ms_reservas.DTO.ReservaRequestDTO;
import com.msreservas.ms_reservas.Exception.ResourceNotFoundException;
import com.msreservas.ms_reservas.Mapper.ReservaMapper;
import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Model.Reserva;
import com.msreservas.ms_reservas.Repository.EstadoReservaRepository;
import com.msreservas.ms_reservas.Repository.ReservaRepository;
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
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @Mock
    private EstadoReservaRepository estadoReservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void findAll_CuandoExistenReservas_DebeRetornarListaDTO() {
        Reserva reserva = new Reserva();
        reserva.setId(1);
        reserva.setNombreCliente("Joel Araya");

        ReservaDTO dto = new ReservaDTO();
        dto.setId(1);
        dto.setNombreCliente("Joel Araya");

        when(reservaRepository.findAll()).thenReturn(List.of(reserva));
        when(reservaMapper.toReservaDTO(reserva)).thenReturn(dto);

        List<ReservaDTO> resultado = reservaService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Joel Araya", resultado.get(0).getNombreCliente());

        verify(reservaRepository, times(1)).findAll();
        verify(reservaMapper, times(1)).toReservaDTO(reserva);
    }

    @Test
    void findById_CuandoReservaExiste_DebeRetornarDTO() {
        Integer id = 1;

        Reserva reserva = new Reserva();
        reserva.setId(id);
        reserva.setNombreCliente("Joel Araya");

        ReservaDTO dto = new ReservaDTO();
        dto.setId(id);
        dto.setNombreCliente("Joel Araya");

        when(reservaRepository.findById(id)).thenReturn(Optional.of(reserva));
        when(reservaMapper.toReservaDTO(reserva)).thenReturn(dto);

        ReservaDTO resultado = reservaService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Joel Araya", resultado.getNombreCliente());

        verify(reservaRepository, times(1)).findById(id);
        verify(reservaMapper, times(1)).toReservaDTO(reserva);
    }

    @Test
    void findById_CuandoReservaNoExiste_DebeLanzarExcepcion() {
        Integer id = 99;

        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> reservaService.findById(id)
        );

        assertEquals("Reserva no encontrada", exception.getMessage());

        verify(reservaRepository, times(1)).findById(id);
    }

    @Test
    void save_CuandoDatosValidos_DebeGuardarReserva() {
        ReservaRequestDTO request = new ReservaRequestDTO();
        request.setClienteId(1);
        request.setNombreCliente("Joel Araya");
        request.setVehiculoId(2);
        request.setCantidadDias(5);
        request.setTotalReserva(175000.0);
        request.setActiva(true);
        request.setFechaReserva(LocalDate.of(2026, 6, 20));
        request.setEstadoReservaId(1);

        EstadoReserva estado = new EstadoReserva();
        estado.setId(1);
        estado.setNombre("Confirmada");

        Reserva reserva = new Reserva();
        reserva.setClienteId(1);
        reserva.setNombreCliente("Joel Araya");
        reserva.setVehiculoId(2);
        reserva.setCantidadDias(5);
        reserva.setTotalReserva(175000.0);
        reserva.setActiva(true);
        reserva.setFechaReserva(LocalDate.of(2026, 6, 20));
        reserva.setEstadoReserva(estado);

        Reserva guardada = new Reserva();
        guardada.setId(1);
        guardada.setClienteId(1);
        guardada.setNombreCliente("Joel Araya");
        guardada.setVehiculoId(2);
        guardada.setCantidadDias(5);
        guardada.setTotalReserva(175000.0);
        guardada.setActiva(true);
        guardada.setFechaReserva(LocalDate.of(2026, 6, 20));
        guardada.setEstadoReserva(estado);

        ReservaDTO response = new ReservaDTO();
        response.setId(1);
        response.setClienteId(1);
        response.setNombreCliente("Joel Araya");
        response.setVehiculoId(2);
        response.setCantidadDias(5);
        response.setTotalReserva(175000.0);
        response.setActiva(true);
        response.setFechaReserva(LocalDate.of(2026, 6, 20));
        response.setEstadoReservaId(1);

        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estado));
        when(reservaMapper.toEntity(request)).thenReturn(reserva);
        when(reservaRepository.save(reserva)).thenReturn(guardada);
        when(reservaMapper.toReservaDTO(guardada)).thenReturn(response);

        ReservaDTO resultado = reservaService.save(request);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Joel Araya", resultado.getNombreCliente());
        assertEquals(175000.0, resultado.getTotalReserva());

        verify(estadoReservaRepository, times(1)).findById(1);
        verify(reservaMapper, times(1)).toEntity(request);
        verify(reservaRepository, times(1)).save(reserva);
        verify(reservaMapper, times(1)).toReservaDTO(guardada);
    }

    @Test
    void save_CuandoEstadoNoExiste_DebeLanzarExcepcion() {
        ReservaRequestDTO request = new ReservaRequestDTO();
        request.setEstadoReservaId(99);

        when(estadoReservaRepository.findById(99)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> reservaService.save(request)
        );

        assertEquals("Estado de reserva no encontrado", exception.getMessage());

        verify(estadoReservaRepository, times(1)).findById(99);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void update_CuandoReservaExiste_DebeActualizarReserva() {
        Integer id = 1;

        ReservaRequestDTO request = new ReservaRequestDTO();
        request.setClienteId(1);
        request.setNombreCliente("Joel Actualizado");
        request.setVehiculoId(3);
        request.setCantidadDias(7);
        request.setTotalReserva(250000.0);
        request.setActiva(true);
        request.setFechaReserva(LocalDate.of(2026, 6, 22));
        request.setEstadoReservaId(1);

        EstadoReserva estado = new EstadoReserva();
        estado.setId(1);
        estado.setNombre("Confirmada");

        Reserva existente = new Reserva();
        existente.setId(id);
        existente.setNombreCliente("Joel Araya");

        Reserva actualizada = new Reserva();
        actualizada.setId(id);
        actualizada.setClienteId(1);
        actualizada.setNombreCliente("Joel Actualizado");
        actualizada.setVehiculoId(3);
        actualizada.setCantidadDias(7);
        actualizada.setTotalReserva(250000.0);
        actualizada.setActiva(true);
        actualizada.setFechaReserva(LocalDate.of(2026, 6, 22));
        actualizada.setEstadoReserva(estado);

        ReservaDTO response = new ReservaDTO();
        response.setId(id);
        response.setNombreCliente("Joel Actualizado");
        response.setVehiculoId(3);
        response.setCantidadDias(7);
        response.setTotalReserva(250000.0);
        response.setActiva(true);
        response.setFechaReserva(LocalDate.of(2026, 6, 22));
        response.setEstadoReservaId(1);

        when(reservaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estado));
        when(reservaRepository.save(existente)).thenReturn(actualizada);
        when(reservaMapper.toReservaDTO(actualizada)).thenReturn(response);

        ReservaDTO resultado = reservaService.update(id, request);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Joel Actualizado", resultado.getNombreCliente());
        assertEquals(250000.0, resultado.getTotalReserva());

        verify(reservaRepository, times(1)).findById(id);
        verify(estadoReservaRepository, times(1)).findById(1);
        verify(reservaRepository, times(1)).save(existente);
    }

    @Test
    void update_CuandoReservaNoExiste_DebeLanzarExcepcion() {
        Integer id = 99;

        ReservaRequestDTO request = new ReservaRequestDTO();

        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> reservaService.update(id, request)
        );

        assertEquals("Reserva no encontrada", exception.getMessage());

        verify(reservaRepository, times(1)).findById(id);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void delete_CuandoReservaExiste_DebeEliminarCorrectamente() {
        Integer id = 1;

        when(reservaRepository.existsById(id)).thenReturn(true);

        reservaService.delete(id);

        verify(reservaRepository, times(1)).existsById(id);
        verify(reservaRepository, times(1)).deleteById(id);
    }

    @Test
    void delete_CuandoReservaNoExiste_DebeLanzarExcepcion() {
        Integer id = 99;

        when(reservaRepository.existsById(id)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> reservaService.delete(id)
        );

        assertEquals("Reserva no encontrada", exception.getMessage());

        verify(reservaRepository, times(1)).existsById(id);
        verify(reservaRepository, never()).deleteById(id);
    }

    @Test
    void buscarActivasPorDiasMayor_CuandoExistenReservas_DebeRetornarListaDTO() {
        Integer dias = 3;

        Reserva reserva = new Reserva();
        reserva.setId(1);
        reserva.setActiva(true);
        reserva.setCantidadDias(5);

        ReservaDTO dto = new ReservaDTO();
        dto.setId(1);
        dto.setActiva(true);
        dto.setCantidadDias(5);

        when(reservaRepository.findByActivaTrueAndCantidadDiasGreaterThan(dias))
                .thenReturn(List.of(reserva));
        when(reservaMapper.toReservaDTO(reserva)).thenReturn(dto);

        List<ReservaDTO> resultado = reservaService.buscarActivasPorDiasMayor(dias);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isActiva());

        verify(reservaRepository, times(1))
                .findByActivaTrueAndCantidadDiasGreaterThan(dias);
    }

    @Test
    void buscarDesdeFecha_CuandoExistenReservas_DebeRetornarListaDTO() {
        LocalDate fecha = LocalDate.of(2026, 6, 1);

        Reserva reserva = new Reserva();
        reserva.setId(1);
        reserva.setFechaReserva(LocalDate.of(2026, 6, 20));

        ReservaDTO dto = new ReservaDTO();
        dto.setId(1);
        dto.setFechaReserva(LocalDate.of(2026, 6, 20));

        when(reservaRepository.buscarReservasDesdeFecha(fecha))
                .thenReturn(List.of(reserva));
        when(reservaMapper.toReservaDTO(reserva)).thenReturn(dto);

        List<ReservaDTO> resultado = reservaService.buscarDesdeFecha(fecha);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(LocalDate.of(2026, 6, 20), resultado.get(0).getFechaReserva());

        verify(reservaRepository, times(1)).buscarReservasDesdeFecha(fecha);
    }
}

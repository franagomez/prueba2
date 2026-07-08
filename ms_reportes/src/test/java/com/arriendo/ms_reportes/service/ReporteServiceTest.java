package com.arriendo.ms_reportes.service;

import com.arriendo.ms_reportes.client.PagoClient;
import com.arriendo.ms_reportes.client.ReservaClient;
import com.arriendo.ms_reportes.dto.ReporteRequestDTO;
import com.arriendo.ms_reportes.dto.ReporteResponseDTO;
import com.arriendo.ms_reportes.exception.ResourceNotFoundException;
import com.arriendo.ms_reportes.model.Reporte;
import com.arriendo.ms_reportes.repository.ReporteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private PagoClient pagoClient;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    void obtenerTodos_CuandoExistenReportes_DebeRetornarLista() {
        Reporte reporte = new Reporte(
                1L,
                "Reporte Mensual",
                "Ingresos del mes",
                500000.0,
                3,
                LocalDate.of(2026, 6, 19)
        );

        when(reporteRepository.findAll()).thenReturn(List.of(reporte));

        List<Reporte> resultado = reporteService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Reporte Mensual", resultado.get(0).getTipoReporte());

        verify(reporteRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_CuandoReporteExiste_DebeRetornarReporte() {
        Long id = 1L;

        Reporte reporte = new Reporte(
                id,
                "Reporte Diario",
                "Reporte de reservas",
                350000.0,
                2,
                LocalDate.of(2026, 6, 19)
        );

        when(reporteRepository.findById(id)).thenReturn(Optional.of(reporte));

        Reporte resultado = reporteService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Reporte Diario", resultado.getTipoReporte());

        verify(reporteRepository, times(1)).findById(id);
    }

    @Test
    void obtenerPorId_CuandoReporteNoExiste_DebeLanzarResourceNotFoundException() {
        Long id = 99L;

        when(reporteRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> reporteService.obtenerPorId(id)
        );

        assertEquals("Reporte no encontrado", exception.getMessage());

        verify(reporteRepository, times(1)).findById(id);
    }

    @Test
    void guardar_CuandoDatosValidos_DebeRetornarReporteResponseDTO() {
        ReporteRequestDTO requestDTO = new ReporteRequestDTO();
        requestDTO.setTipoReporte("Reporte Mensual");
        requestDTO.setDescripcion("Reporte de ingresos");
        requestDTO.setTotalIngresos(700000.0);
        requestDTO.setTotalReservas(4);
        requestDTO.setFechaGeneracion(LocalDate.of(2026, 6, 19));

        Reporte reporteGuardado = new Reporte(
                1L,
                "Reporte Mensual",
                "Reporte de ingresos",
                700000.0,
                4,
                LocalDate.of(2026, 6, 19)
        );

        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteGuardado);

        ReporteResponseDTO resultado = reporteService.guardar(requestDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Reporte Mensual", resultado.getTipoReporte());
        assertEquals(700000.0, resultado.getTotalIngresos());

        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    void obtenerPagos_CuandoMsPagosResponde_DebeRetornarListaDePagos() {
        List<Map<String, Object>> pagos = List.of(
                Map.of(
                        "id", 1,
                        "monto", 200000.0,
                        "metodoPago", "Tarjeta"
                )
        );

        Map<String, Object> respuestaHal = Map.of(
                "_embedded", Map.of("pagoList", pagos)
        );

        when(pagoClient.obtenerPagos()).thenReturn(respuestaHal);

        List<Map<String, Object>> resultado = reporteService.obtenerPagos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tarjeta", resultado.get(0).get("metodoPago"));

        verify(pagoClient, times(1)).obtenerPagos();
    }

    @Test
    void obtenerReservas_CuandoMsReservasResponde_DebeRetornarListaDeReservas() {
        List<Map<String, Object>> reservas = List.of(
                Map.of(
                        "id", 1,
                        "nombreCliente", "Ghislaine",
                        "totalReserva", 350000.0
                )
        );

        Map<String, Object> respuestaHal = Map.of(
                "_embedded", Map.of("reservaList", reservas)
        );

        when(reservaClient.obtenerReservas()).thenReturn(respuestaHal);

        List<Map<String, Object>> resultado = reporteService.obtenerReservas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ghislaine", resultado.get(0).get("nombreCliente"));

        verify(reservaClient, times(1)).obtenerReservas();
    }
}
package com.arriendo.ms_pagos.service;

import com.arriendo.ms_pagos.dto.PagoRequestDTO;
import com.arriendo.ms_pagos.dto.PagoResponseDTO;
import com.arriendo.ms_pagos.exception.ResourceNotFoundException;
import com.arriendo.ms_pagos.model.Pago;
import com.arriendo.ms_pagos.repository.PagoRepository;
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
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void obtenerTodos_debeRetornarListaDePagos() {
        Pago pago = crearPago();

        when(pagoRepository.findAll()).thenReturn(List.of(pago));

        List<Pago> resultado = pagoService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Tarjeta", resultado.get(0).getMetodoPago());
        verify(pagoRepository).findAll();
    }

    @Test
    void obtenerPorId_debeRetornarPago() {
        Pago pago = crearPago();

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        Pago resultado = pagoService.obtenerPorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals(200000.0, resultado.getMonto());
        verify(pagoRepository).findById(1L);
    }

    @Test
    void obtenerPorId_debeLanzarExcepcionSiNoExiste() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pagoService.obtenerPorId(99L));

        verify(pagoRepository).findById(99L);
    }

    @Test
    void buscarPorRango_debeRetornarPagos() {
        Pago pago = crearPago();

        when(pagoRepository.buscarPagosPorRango(10000.0, 300000.0))
                .thenReturn(List.of(pago));

        List<Pago> resultado = pagoService.buscarPorRango(10000.0, 300000.0);

        assertEquals(1, resultado.size());
        verify(pagoRepository).buscarPagosPorRango(10000.0, 300000.0);
    }

    @Test
    void guardar_debeGuardarPago() {
        PagoRequestDTO dto = crearRequestDTO();
        Pago pago = crearPago();

        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        PagoResponseDTO resultado = pagoService.guardar(dto);

        assertNotNull(resultado);
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    void actualizar_debeActualizarPago() {
        Pago pago = crearPago();
        PagoRequestDTO dto = crearRequestDTO();

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        Pago resultado = pagoService.actualizar(1L, dto);

        assertEquals("Tarjeta", resultado.getMetodoPago());
        assertEquals(200000.0, resultado.getMonto());
        verify(pagoRepository).save(pago);
    }

    @Test
    void eliminar_debeEliminarPago() {
        Pago pago = crearPago();

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        pagoService.eliminar(1L);

        verify(pagoRepository).delete(pago);
    }

    private Pago crearPago() {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setMonto(200000.0);
        pago.setMetodoPago("Tarjeta");
        pago.setPagado(true);
        pago.setFechaPago(LocalDate.of(2025, 1, 1));
        pago.setDescripcion("Pago actualizado");
        return pago;
    }

    private PagoRequestDTO crearRequestDTO() {
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setMonto(200000.0);
        dto.setMetodoPago("Tarjeta");
        dto.setPagado(true);
        dto.setFechaPago(LocalDate.of(2025, 1, 1));
        dto.setDescripcion("Pago actualizado");
        return dto;
    }
}
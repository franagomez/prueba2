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
    void guardarPago_CuandoDatosValidos_DebeRetornarPagoResponseDTO() {
        // Given
        PagoRequestDTO requestDTO = new PagoRequestDTO();
        requestDTO.setMonto(200000.0);
        requestDTO.setMetodoPago("Tarjeta");
        requestDTO.setPagado(true);
        requestDTO.setFechaPago(LocalDate.of(2026, 6, 19));
        requestDTO.setDescripcion("Pago de reserva");

        Pago pagoGuardado = new Pago();
        pagoGuardado.setId(1L);
        pagoGuardado.setMonto(200000.0);
        pagoGuardado.setMetodoPago("Tarjeta");
        pagoGuardado.setPagado(true);
        pagoGuardado.setFechaPago(LocalDate.of(2026, 6, 19));
        pagoGuardado.setDescripcion("Pago de reserva");

        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoGuardado);

        // When
        PagoResponseDTO resultado = pagoService.guardar(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(200000.0, resultado.getMonto());
        assertEquals("Tarjeta", resultado.getMetodoPago());
        assertTrue(resultado.getPagado());

        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    void obtenerPorId_CuandoPagoExiste_DebeRetornarPago() {
        // Given
        Long id = 1L;

        Pago pago = new Pago();
        pago.setId(id);
        pago.setMonto(150000.0);
        pago.setMetodoPago("Efectivo");
        pago.setPagado(true);
        pago.setFechaPago(LocalDate.of(2026, 6, 19));
        pago.setDescripcion("Pago encontrado");

        when(pagoRepository.findById(id)).thenReturn(Optional.of(pago));

        // When
        Pago resultado = pagoService.obtenerPorId(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Efectivo", resultado.getMetodoPago());

        verify(pagoRepository, times(1)).findById(id);
    }

    @Test
    void obtenerPorId_CuandoPagoNoExiste_DebeLanzarResourceNotFoundException() {
        // Given
        Long id = 99L;

        when(pagoRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> pagoService.obtenerPorId(id)
        );

        assertEquals("Pago no encontrado", exception.getMessage());

        verify(pagoRepository, times(1)).findById(id);
    }

    @Test
    void eliminar_CuandoPagoExiste_DebeEliminarPago() {
        // Given
        Long id = 1L;

        Pago pago = new Pago();
        pago.setId(id);
        pago.setMonto(100000.0);
        pago.setMetodoPago("Transferencia");
        pago.setPagado(true);
        pago.setFechaPago(LocalDate.of(2026, 6, 19));
        pago.setDescripcion("Pago a eliminar");

        when(pagoRepository.findById(id)).thenReturn(Optional.of(pago));

        // When
        pagoService.eliminar(id);

        // Then
        verify(pagoRepository, times(1)).findById(id);
        verify(pagoRepository, times(1)).delete(pago);
    }
}

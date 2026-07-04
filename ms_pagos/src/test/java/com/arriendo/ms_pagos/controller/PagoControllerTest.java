package com.arriendo.ms_pagos.controller;

import com.arriendo.ms_pagos.assembler.PagoModelAssembler;
import com.arriendo.ms_pagos.dto.PagoRequestDTO;
import com.arriendo.ms_pagos.dto.PagoResponseDTO;
import com.arriendo.ms_pagos.exception.ResourceNotFoundException;
import com.arriendo.ms_pagos.model.Pago;
import com.arriendo.ms_pagos.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagoController.class)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PagoService pagoService;

    @MockitoBean
    private PagoModelAssembler pagoModelAssembler;

    @Test
    void listar_debeRetornar200ConPagos() throws Exception {
        Pago pago = crearPago();

        when(pagoService.obtenerTodos()).thenReturn(List.of(pago));
        when(pagoModelAssembler.toCollectionModel(any()))
                .thenReturn(org.springframework.hateoas.CollectionModel.of(List.of(EntityModel.of(pago))));

        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._embedded.pagoList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.pagoList[0].metodoPago").value("Tarjeta"));
    }

    @Test
    void buscarPorId_existente_debeRetornar200() throws Exception {
        Pago pago = crearPago();

        when(pagoService.obtenerPorId(1L)).thenReturn(pago);
        when(pagoModelAssembler.toModel(pago)).thenReturn(EntityModel.of(pago));

        mockMvc.perform(get("/api/v1/pagos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.metodoPago").value("Tarjeta"));
    }

    @Test
    void buscarPorId_inexistente_debeRetornar404() throws Exception {
        when(pagoService.obtenerPorId(99L))
                .thenThrow(new ResourceNotFoundException("Pago no encontrado"));

        mockMvc.perform(get("/api/v1/pagos/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Pago no encontrado"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void guardar_debeRetornar201() throws Exception {
        PagoRequestDTO dto = crearRequestDTO();
        PagoResponseDTO responseDTO = new PagoResponseDTO(
                1L, 200000.0, "Tarjeta", true, LocalDate.of(2025, 1, 1), "Pago arriendo vehículo");

        when(pagoService.guardar(any(PagoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/pagos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.metodoPago").value("Tarjeta"));
    }

    private Pago crearPago() {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setMonto(200000.0);
        pago.setMetodoPago("Tarjeta");
        pago.setPagado(true);
        pago.setFechaPago(LocalDate.of(2025, 1, 1));
        pago.setDescripcion("Pago arriendo vehículo");
        return pago;
    }

    private PagoRequestDTO crearRequestDTO() {
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setMonto(200000.0);
        dto.setMetodoPago("Tarjeta");
        dto.setPagado(true);
        dto.setFechaPago(LocalDate.of(2025, 1, 1));
        dto.setDescripcion("Pago arriendo vehículo");
        return dto;
    }
}

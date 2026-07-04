package com.arriendo.ms_sucursales.controller;

import com.arriendo.ms_sucursales.assembler.RegionModelAssembler;
import com.arriendo.ms_sucursales.dto.RegionRequestDTO;
import com.arriendo.ms_sucursales.dto.RegionResponseDTO;
import com.arriendo.ms_sucursales.exception.ResourceNotFoundException;
import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.service.RegionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegionController.class)
class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegionService regionService;

    @MockitoBean
    private RegionModelAssembler regionModelAssembler;

    private Region crearRegion(Integer id, String nombre) {
        return new Region(id, nombre, "RM", 13, true, LocalDate.of(2025, 1, 1));
    }

    @Test
    void listar_DebeRetornar200ConListaDeRegiones() throws Exception {
        Region region = crearRegion(1, "Metropolitana");

        when(regionService.obtenerTodas()).thenReturn(List.of(region));
        when(regionModelAssembler.toCollectionModel(any()))
                .thenReturn(CollectionModel.of(List.of(EntityModel.of(region))));

        mockMvc.perform(get("/api/v1/regiones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.regionList[0].nombre").value("Metropolitana"));
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornar200() throws Exception {
        Region region = crearRegion(1, "Metropolitana");

        when(regionService.obtenerPorId(1)).thenReturn(region);
        when(regionModelAssembler.toModel(region)).thenReturn(EntityModel.of(region));

        mockMvc.perform(get("/api/v1/regiones/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Metropolitana"));
    }

    @Test
    void buscarPorId_CuandoNoExiste_DebeRetornar404() throws Exception {
        when(regionService.obtenerPorId(99))
                .thenThrow(new ResourceNotFoundException("Región no encontrada"));

        mockMvc.perform(get("/api/v1/regiones/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Región no encontrada"));
    }

    @Test
    void guardar_CuandoDatosValidos_DebeRetornar201() throws Exception {
        RegionRequestDTO dto = new RegionRequestDTO();
        dto.setNombre("Valparaíso");
        dto.setCodigo("VAL");
        dto.setNumeroRegion(5);
        dto.setActivo(true);
        dto.setFechaCreacion(LocalDate.of(2023, 1, 1));

        RegionResponseDTO respuesta = new RegionResponseDTO(
                1, "Valparaíso", "VAL", 5, true, LocalDate.of(2023, 1, 1)
        );

        when(regionService.guardar(any(RegionRequestDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/v1/regiones")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Valparaíso"));
    }
}

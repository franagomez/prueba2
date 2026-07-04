package com.arriendo.ms_sucursales.controller;

import com.arriendo.ms_sucursales.assembler.SucursalModelAssembler;
import com.arriendo.ms_sucursales.dto.SucursalRequestDTO;
import com.arriendo.ms_sucursales.dto.SucursalResponseDTO;
import com.arriendo.ms_sucursales.exception.ResourceNotFoundException;
import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.model.Sucursal;
import com.arriendo.ms_sucursales.service.SucursalService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SucursalController.class)
class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SucursalService sucursalService;

    @MockitoBean
    private SucursalModelAssembler sucursalModelAssembler;

    private Sucursal crearSucursal(Integer id, String nombre) {
        Region region = new Region(1, "Metropolitana", "RM", 13, true, LocalDate.of(2025, 1, 1));
        return new Sucursal(id, nombre, "Av. Alameda 123", 50, true,
                LocalDate.of(2020, 5, 10), region);
    }

    @Test
    void listar_DebeRetornar200ConListaDeSucursales() throws Exception {
        Sucursal sucursal = crearSucursal(1, "Sucursal Santiago Centro");

        when(sucursalService.obtenerTodas()).thenReturn(List.of(sucursal));
        when(sucursalModelAssembler.toCollectionModel(any()))
                .thenReturn(org.springframework.hateoas.CollectionModel.of(
                        List.of(EntityModel.of(sucursal))));

        mockMvc.perform(get("/api/v1/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.sucursalList[0].nombre")
                        .value("Sucursal Santiago Centro"));
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornar200() throws Exception {
        Sucursal sucursal = crearSucursal(1, "Sucursal Santiago Centro");

        when(sucursalService.obtenerPorId(1)).thenReturn(sucursal);
        when(sucursalModelAssembler.toModel(sucursal)).thenReturn(EntityModel.of(sucursal));

        mockMvc.perform(get("/api/v1/sucursales/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sucursal Santiago Centro"));
    }

    @Test
    void buscarPorId_CuandoNoExiste_DebeRetornar404() throws Exception {
        when(sucursalService.obtenerPorId(99))
                .thenThrow(new ResourceNotFoundException("Sucursal no encontrada"));

        mockMvc.perform(get("/api/v1/sucursales/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Sucursal no encontrada"));
    }

    @Test
    void guardar_CuandoDatosValidos_DebeRetornar201() throws Exception {
        SucursalRequestDTO dto = new SucursalRequestDTO();
        dto.setNombre("Sucursal Providencia");
        dto.setDireccion("Providencia 100");
        dto.setCapacidadVehiculos(40);
        dto.setOperativa(true);
        dto.setFechaApertura(LocalDate.of(2023, 1, 1));
        dto.setRegionId(1);

        SucursalResponseDTO respuesta = new SucursalResponseDTO(
                1, "Sucursal Providencia", "Providencia 100", 40, true,
                LocalDate.of(2023, 1, 1), 1, "Metropolitana"
        );

        when(sucursalService.guardar(any(SucursalRequestDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/v1/sucursales")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Sucursal Providencia"));
    }
}

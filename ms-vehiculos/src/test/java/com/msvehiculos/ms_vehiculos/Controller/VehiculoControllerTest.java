package com.msvehiculos.ms_vehiculos.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msvehiculos.ms_vehiculos.Assembler.VehiculoModelAssembler;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoDTO;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoRequestDTO;
import com.msvehiculos.ms_vehiculos.Exception.ResourceNotFoundException;
import com.msvehiculos.ms_vehiculos.Service.VehiculoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehiculoController.class)
class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehiculoService vehiculoService;

    @MockBean
    private VehiculoModelAssembler vehiculoModelAssembler;

    private VehiculoDTO vehiculoDTO() {
        return new VehiculoDTO(
                1,
                "AA1122",
                "Toyota",
                "Rav4",
                2023,
                30000.0,
                true,
                LocalDate.of(2026, 6, 20),
                1
        );
    }

    private VehiculoRequestDTO vehiculoRequestDTO() {
        return new VehiculoRequestDTO(
                "AA1122",
                "Toyota",
                "Rav4",
                2023,
                30000.0,
                true,
                LocalDate.of(2026, 6, 20),
                1
        );
    }

    @Test
    void listar_debeRetornarVehiculosConLinksHateoas() throws Exception {
        VehiculoDTO dto = vehiculoDTO();
        when(vehiculoService.findAll()).thenReturn(List.of(dto));
        when(vehiculoModelAssembler.toModel(any(VehiculoDTO.class)))
                .thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v1/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.vehiculoDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.vehiculoDTOList[0].patente").value("AA1122"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void listar_sinVehiculos_debeRetornarNoContent() throws Exception {
        when(vehiculoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/vehiculos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_debeRetornarVehiculoConLinks() throws Exception {
        VehiculoDTO dto = vehiculoDTO();
        when(vehiculoService.findById(1)).thenReturn(dto);
        when(vehiculoModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/vehiculos/1", "self")));

        mockMvc.perform(get("/api/v1/vehiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.marca").value("Toyota"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void buscarPorId_noExistente_debeRetornar404() throws Exception {
        when(vehiculoService.findById(anyInt()))
                .thenThrow(new ResourceNotFoundException("Vehiculo no encontrado"));

        mockMvc.perform(get("/api/v1/vehiculos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_debeRetornarVehiculoCreadoConLinks() throws Exception {
        VehiculoDTO dto = vehiculoDTO();
        when(vehiculoService.save(any())).thenReturn(dto);
        when(vehiculoModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/vehiculos/1", "self")));

        mockMvc.perform(post("/api/v1/vehiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehiculoRequestDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void guardar_categoriaNoExistente_debeRetornar404() throws Exception {
        when(vehiculoService.save(any()))
                .thenThrow(new ResourceNotFoundException("Categoria no encontrada"));

        mockMvc.perform(post("/api/v1/vehiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehiculoRequestDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_datosInvalidos_debeRetornar400() throws Exception {
        VehiculoRequestDTO invalido = new VehiculoRequestDTO(
                "", "", "", null, null, true, null, null);

        mockMvc.perform(post("/api/v1/vehiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_debeRetornarVehiculoActualizadoConLinks() throws Exception {
        VehiculoDTO dto = vehiculoDTO();
        when(vehiculoService.update(any(Integer.class), any())).thenReturn(dto);
        when(vehiculoModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/vehiculos/1", "self")));

        mockMvc.perform(put("/api/v1/vehiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehiculoRequestDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void actualizar_noExistente_debeRetornar404() throws Exception {
        when(vehiculoService.update(any(Integer.class), any()))
                .thenThrow(new ResourceNotFoundException("Vehiculo no encontrado"));

        mockMvc.perform(put("/api/v1/vehiculos/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehiculoRequestDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_debeRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/vehiculos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_noExistente_debeRetornar404() throws Exception {
        org.mockito.Mockito.doThrow(new ResourceNotFoundException("Vehiculo no encontrado"))
                .when(vehiculoService).delete(99);

        mockMvc.perform(delete("/api/v1/vehiculos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarDisponiblesPorPrecioMenor_debeRetornarVehiculosConLinks() throws Exception {
        VehiculoDTO dto = vehiculoDTO();
        when(vehiculoService.buscarDisponiblesPorPrecioMenor(anyDouble())).thenReturn(List.of(dto));
        when(vehiculoModelAssembler.toModel(any(VehiculoDTO.class)))
                .thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v1/vehiculos/disponibles/precio-menor/35000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.vehiculoDTOList[0].id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void buscarDisponiblesPorPrecioMenor_sinResultados_debeRetornarNoContent() throws Exception {
        when(vehiculoService.buscarDisponiblesPorPrecioMenor(anyDouble())).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/vehiculos/disponibles/precio-menor/35000"))
                .andExpect(status().isNoContent());
    }
}

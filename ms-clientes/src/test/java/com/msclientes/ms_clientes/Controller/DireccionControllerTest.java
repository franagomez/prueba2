package com.msclientes.ms_clientes.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msclientes.ms_clientes.Assembler.DireccionModelAssembler;
import com.msclientes.ms_clientes.DTO.DireccionDTO;
import com.msclientes.ms_clientes.DTO.DireccionRequestDTO;
import com.msclientes.ms_clientes.Exception.ResourceNotFoundException;
import com.msclientes.ms_clientes.Service.DireccionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DireccionController.class)
class DireccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DireccionService direccionService;

    @MockitoBean
    private DireccionModelAssembler direccionModelAssembler;

    private DireccionDTO direccionDTO() {
        return new DireccionDTO(
                1,
                "Av. Providencia",
                "1234",
                "Providencia",
                "Santiago",
                7500000,
                true,
                LocalDate.of(2026, 3, 1),
                1
        );
    }

    private DireccionRequestDTO direccionRequestDTO() {
        return new DireccionRequestDTO(
                "Av. Providencia",
                "1234",
                "Providencia",
                "Santiago",
                7500000,
                true,
                LocalDate.of(2026, 3, 1),
                1
        );
    }

    @Test
    void listar_debeRetornarDireccionesConLinksHateoas() throws Exception {
        DireccionDTO dto = direccionDTO();
        when(direccionService.findAll()).thenReturn(List.of(dto));
        when(direccionModelAssembler.toModel(any(DireccionDTO.class)))
                .thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v1/direcciones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.direccionDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.direccionDTOList[0].calle").value("Av. Providencia"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void listar_sinDirecciones_debeRetornarNoContent() throws Exception {
        when(direccionService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/direcciones"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_debeRetornarDireccionConLinks() throws Exception {
        DireccionDTO dto = direccionDTO();
        when(direccionService.findById(1)).thenReturn(dto);
        when(direccionModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/direcciones/1", "self")));

        mockMvc.perform(get("/api/v1/direcciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comuna").value("Providencia"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void buscarPorId_noExistente_debeRetornar404() throws Exception {
        when(direccionService.findById(anyInt()))
                .thenThrow(new ResourceNotFoundException("Dirección no encontrada"));

        mockMvc.perform(get("/api/v1/direcciones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_debeRetornarDireccionCreadaConLinks() throws Exception {
        DireccionDTO dto = direccionDTO();
        when(direccionService.save(any())).thenReturn(dto);
        when(direccionModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/direcciones/1", "self")));

        mockMvc.perform(post("/api/v1/direcciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(direccionRequestDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void guardar_clienteNoExistente_debeRetornar404() throws Exception {
        when(direccionService.save(any()))
                .thenThrow(new ResourceNotFoundException("Cliente no encontrado"));

        mockMvc.perform(post("/api/v1/direcciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(direccionRequestDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_datosInvalidos_debeRetornar400() throws Exception {
        DireccionRequestDTO invalido = new DireccionRequestDTO(
                "", "", "", "", null, true, null, null);

        mockMvc.perform(post("/api/v1/direcciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_debeRetornarDireccionActualizadaConLinks() throws Exception {
        DireccionDTO dto = direccionDTO();
        when(direccionService.update(any(Integer.class), any())).thenReturn(dto);
        when(direccionModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/direcciones/1", "self")));

        mockMvc.perform(put("/api/v1/direcciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(direccionRequestDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void actualizar_noExistente_debeRetornar404() throws Exception {
        when(direccionService.update(any(Integer.class), any()))
                .thenThrow(new ResourceNotFoundException("Dirección no encontrada"));

        mockMvc.perform(put("/api/v1/direcciones/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(direccionRequestDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_debeRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/direcciones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_noExistente_debeRetornar404() throws Exception {
        org.mockito.Mockito.doThrow(new ResourceNotFoundException("Dirección no encontrada"))
                .when(direccionService).delete(99);

        mockMvc.perform(delete("/api/v1/direcciones/99"))
                .andExpect(status().isNotFound());
    }
}
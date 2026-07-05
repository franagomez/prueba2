package com.msreservas.ms_reservas.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msreservas.ms_reservas.Assembler.EstadoReservaModelAssembler;
import com.msreservas.ms_reservas.DTO.EstadoReservaDTO;
import com.msreservas.ms_reservas.DTO.EstadoReservaRequestDTO;
import com.msreservas.ms_reservas.Exception.ResourceNotFoundException;
import com.msreservas.ms_reservas.Service.EstadoReservaService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstadoReservaController.class)
class EstadoReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EstadoReservaService estadoReservaService;

    @MockBean
    private EstadoReservaModelAssembler estadoReservaModelAssembler;

    private EstadoReservaDTO estadoReservaDTO() {
        EstadoReservaDTO dto = new EstadoReservaDTO();
        dto.setId(1);
        dto.setNombre("Confirmada");
        dto.setDescripcion("Reserva confirmada por el sistema");
        dto.setPrioridad(1);
        dto.setActivo(true);
        dto.setFechaCreacion(LocalDate.of(2026, 6, 20));
        return dto;
    }

    private EstadoReservaRequestDTO estadoReservaRequestDTO() {
        return new EstadoReservaRequestDTO(
                "Confirmada", "Reserva confirmada por el sistema", 1, true,
                LocalDate.of(2026, 6, 20));
    }

    @Test
    void listar_debeRetornarEstadosConLinksHateoas() throws Exception {
        EstadoReservaDTO dto = estadoReservaDTO();
        when(estadoReservaService.findAll()).thenReturn(List.of(dto));
        when(estadoReservaModelAssembler.toModel(any(EstadoReservaDTO.class)))
                .thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v1/estados-reserva"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.estadoReservaDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.estadoReservaDTOList[0].nombre").value("Confirmada"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void listar_sinEstados_debeRetornarNoContent() throws Exception {
        when(estadoReservaService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/estados-reserva"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_debeRetornarEstadoConLinks() throws Exception {
        EstadoReservaDTO dto = estadoReservaDTO();
        when(estadoReservaService.findById(1)).thenReturn(dto);
        when(estadoReservaModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/estados-reserva/1", "self")));

        mockMvc.perform(get("/api/v1/estados-reserva/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Confirmada"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void buscarPorId_noExistente_debeRetornar404() throws Exception {
        when(estadoReservaService.findById(anyInt()))
                .thenThrow(new ResourceNotFoundException("Estado de reserva no encontrado"));

        mockMvc.perform(get("/api/v1/estados-reserva/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_debeRetornarEstadoCreadoConLinks() throws Exception {
        EstadoReservaDTO dto = estadoReservaDTO();
        when(estadoReservaService.save(any())).thenReturn(dto);
        when(estadoReservaModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/estados-reserva/1", "self")));

        mockMvc.perform(post("/api/v1/estados-reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estadoReservaRequestDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void guardar_datosInvalidos_debeRetornar400() throws Exception {
        EstadoReservaRequestDTO invalido = new EstadoReservaRequestDTO(
                "", "", null, true, null);

        mockMvc.perform(post("/api/v1/estados-reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_debeRetornarEstadoActualizadoConLinks() throws Exception {
        EstadoReservaDTO dto = estadoReservaDTO();
        when(estadoReservaService.update(any(Integer.class), any())).thenReturn(dto);
        when(estadoReservaModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/estados-reserva/1", "self")));

        mockMvc.perform(put("/api/v1/estados-reserva/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estadoReservaRequestDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void actualizar_noExistente_debeRetornar404() throws Exception {
        when(estadoReservaService.update(any(Integer.class), any()))
                .thenThrow(new ResourceNotFoundException("Estado de reserva no encontrado"));

        mockMvc.perform(put("/api/v1/estados-reserva/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estadoReservaRequestDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_debeRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/estados-reserva/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_noExistente_debeRetornar404() throws Exception {
        doThrow(new ResourceNotFoundException("Estado de reserva no encontrado"))
                .when(estadoReservaService).delete(99);

        mockMvc.perform(delete("/api/v1/estados-reserva/99"))
                .andExpect(status().isNotFound());
    }
}

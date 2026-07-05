package com.msreservas.ms_reservas.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msreservas.ms_reservas.Assembler.ReservaModelAssembler;
import com.msreservas.ms_reservas.DTO.ReservaDTO;
import com.msreservas.ms_reservas.DTO.ReservaRequestDTO;
import com.msreservas.ms_reservas.Exception.ResourceNotFoundException;
import com.msreservas.ms_reservas.Service.ReservaService;
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

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservaService reservaService;

    @MockBean
    private ReservaModelAssembler reservaModelAssembler;

    private ReservaDTO reservaDTO() {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(1);
        dto.setClienteId(1);
        dto.setNombreCliente("Joel Araya");
        dto.setVehiculoId(2);
        dto.setCantidadDias(5);
        dto.setTotalReserva(175000.0);
        dto.setActiva(true);
        dto.setFechaReserva(LocalDate.of(2026, 6, 20));
        dto.setEstadoReservaId(1);
        return dto;
    }

    private ReservaRequestDTO reservaRequestDTO() {
        return new ReservaRequestDTO(
                1, "Joel Araya", 2, 5, 175000.0, true,
                LocalDate.of(2026, 6, 20), 1);
    }

    @Test
    void listar_debeRetornarReservasConLinksHateoas() throws Exception {
        ReservaDTO dto = reservaDTO();
        when(reservaService.findAll()).thenReturn(List.of(dto));
        when(reservaModelAssembler.toModel(any(ReservaDTO.class)))
                .thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v1/reservas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.reservaDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.reservaDTOList[0].nombreCliente").value("Joel Araya"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void listar_sinReservas_debeRetornarNoContent() throws Exception {
        when(reservaService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/reservas"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_debeRetornarReservaConLinks() throws Exception {
        ReservaDTO dto = reservaDTO();
        when(reservaService.findById(1)).thenReturn(dto);
        when(reservaModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/reservas/1", "self")));

        mockMvc.perform(get("/api/v1/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreCliente").value("Joel Araya"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void buscarPorId_noExistente_debeRetornar404() throws Exception {
        when(reservaService.findById(anyInt()))
                .thenThrow(new ResourceNotFoundException("Reserva no encontrada"));

        mockMvc.perform(get("/api/v1/reservas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_debeRetornarReservaCreadaConLinks() throws Exception {
        ReservaDTO dto = reservaDTO();
        when(reservaService.save(any())).thenReturn(dto);
        when(reservaModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/reservas/1", "self")));

        mockMvc.perform(post("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequestDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void guardar_datosInvalidos_debeRetornar400() throws Exception {
        ReservaRequestDTO invalido = new ReservaRequestDTO(
                null, "", null, null, null, true, null, null);

        mockMvc.perform(post("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_debeRetornarReservaActualizadaConLinks() throws Exception {
        ReservaDTO dto = reservaDTO();
        when(reservaService.update(any(Integer.class), any())).thenReturn(dto);
        when(reservaModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/reservas/1", "self")));

        mockMvc.perform(put("/api/v1/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequestDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void actualizar_noExistente_debeRetornar404() throws Exception {
        when(reservaService.update(any(Integer.class), any()))
                .thenThrow(new ResourceNotFoundException("Reserva no encontrada"));

        mockMvc.perform(put("/api/v1/reservas/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequestDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_debeRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/reservas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_noExistente_debeRetornar404() throws Exception {
        doThrow(new ResourceNotFoundException("Reserva no encontrada"))
                .when(reservaService).delete(99);

        mockMvc.perform(delete("/api/v1/reservas/99"))
                .andExpect(status().isNotFound());
    }
}

package com.msclientes.ms_clientes.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msclientes.ms_clientes.Assembler.ClienteModelAssembler;
import com.msclientes.ms_clientes.DTO.ClienteDTO;
import com.msclientes.ms_clientes.DTO.ClienteRequestDTO;
import com.msclientes.ms_clientes.Exception.ResourceNotFoundException;
import com.msclientes.ms_clientes.Service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private org.springframework.test.web.servlet.MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService clienteService;

    @MockitoBean
    private ClienteModelAssembler clienteModelAssembler;

    private ClienteDTO clienteDTO() {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(1);
        dto.setRun("20456789-3");
        dto.setNombre("Juan");
        dto.setApellido("Pérez");
        dto.setEmail("juan.perez@gmail.com");
        dto.setTelefono("+56912345678");
        dto.setPuntosCliente(150);
        dto.setActivo(true);
        dto.setFechaRegistro(LocalDate.now());
        return dto;
    }

    private ClienteRequestDTO clienteRequestDTO() {
        return new ClienteRequestDTO(
                "20456789-3", "Juan", "Pérez", "juan.perez@gmail.com",
                "+56912345678", 150, true, LocalDate.now());
    }

    @Test
    void listar_debeRetornarClientesConLinksHateoas() throws Exception {
        ClienteDTO dto = clienteDTO();
        when(clienteService.findAll()).thenReturn(List.of(dto));
        when(clienteModelAssembler.toModel(any(ClienteDTO.class)))
                .thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.clienteDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.clienteDTOList[0].nombre").value("Juan"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void listar_sinClientes_debeRetornarNoContent() throws Exception {
        when(clienteService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_debeRetornarClienteConLinks() throws Exception {
        ClienteDTO dto = clienteDTO();
        when(clienteService.findById(1)).thenReturn(dto);
        when(clienteModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto,
                        org.springframework.hateoas.Link.of("/api/v1/clientes/1", "self")));

        mockMvc.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("juan.perez@gmail.com"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void buscarPorId_noExistente_debeRetornar404() throws Exception {
        when(clienteService.findById(anyInt()))
                .thenThrow(new ResourceNotFoundException("Cliente no encontrado"));

        mockMvc.perform(get("/api/v1/clientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_debeRetornarClienteCreadoConLinks() throws Exception {
        ClienteDTO dto = clienteDTO();
        when(clienteService.save(any())).thenReturn(dto);
        when(clienteModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto,
                        org.springframework.hateoas.Link.of("/api/v1/clientes/1", "self")));

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void guardar_datosInvalidos_debeRetornar400() throws Exception {
        ClienteRequestDTO invalido = new ClienteRequestDTO(
                "", "", "", "correo-invalido", "", null, true, null);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_debeRetornarClienteActualizadoConLinks() throws Exception {
        ClienteDTO dto = clienteDTO();
        when(clienteService.update(any(Integer.class), any())).thenReturn(dto);
        when(clienteModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto,
                        org.springframework.hateoas.Link.of("/api/v1/clientes/1", "self")));

        mockMvc.perform(put("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void actualizar_noExistente_debeRetornar404() throws Exception {
        when(clienteService.update(any(Integer.class), any()))
                .thenThrow(new ResourceNotFoundException("Cliente no encontrado"));

        mockMvc.perform(put("/api/v1/clientes/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_debeRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_noExistente_debeRetornar404() throws Exception {
        org.mockito.Mockito.doThrow(new ResourceNotFoundException("Cliente no encontrado"))
                .when(clienteService).delete(99);

        mockMvc.perform(delete("/api/v1/clientes/99"))
                .andExpect(status().isNotFound());
    }
}
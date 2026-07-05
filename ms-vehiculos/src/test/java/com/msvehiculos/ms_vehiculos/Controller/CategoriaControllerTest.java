package com.msvehiculos.ms_vehiculos.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msvehiculos.ms_vehiculos.Assembler.CategoriaModelAssembler;
import com.msvehiculos.ms_vehiculos.DTO.CategoriaDTO;
import com.msvehiculos.ms_vehiculos.DTO.CategoriaRequestDTO;
import com.msvehiculos.ms_vehiculos.Exception.ResourceNotFoundException;
import com.msvehiculos.ms_vehiculos.Service.CategoriaService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoriaService categoriaService;

    @MockBean
    private CategoriaModelAssembler categoriaModelAssembler;

    private CategoriaDTO categoriaDTO() {
        return new CategoriaDTO(
                1,
                "SUV",
                "Vehiculos deportivos utilitarios",
                3,
                true,
                LocalDate.of(2026, 6, 20)
        );
    }

    private CategoriaRequestDTO categoriaRequestDTO() {
        return new CategoriaRequestDTO(
                "SUV",
                "Vehiculos deportivos utilitarios",
                3,
                true,
                LocalDate.of(2026, 6, 20)
        );
    }

    @Test
    void listar_debeRetornarCategoriasConLinksHateoas() throws Exception {
        CategoriaDTO dto = categoriaDTO();
        when(categoriaService.findAll()).thenReturn(List.of(dto));
        when(categoriaModelAssembler.toModel(any(CategoriaDTO.class)))
                .thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.categoriaDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.categoriaDTOList[0].nombre").value("SUV"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void listar_sinCategorias_debeRetornarNoContent() throws Exception {
        when(categoriaService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_debeRetornarCategoriaConLinks() throws Exception {
        CategoriaDTO dto = categoriaDTO();
        when(categoriaService.findById(1)).thenReturn(dto);
        when(categoriaModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/categorias/1", "self")));

        mockMvc.perform(get("/api/v1/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("SUV"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void buscarPorId_noExistente_debeRetornar404() throws Exception {
        when(categoriaService.findById(anyInt()))
                .thenThrow(new ResourceNotFoundException("Categoria no encontrada"));

        mockMvc.perform(get("/api/v1/categorias/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_debeRetornarCategoriaCreadaConLinks() throws Exception {
        CategoriaDTO dto = categoriaDTO();
        when(categoriaService.save(any())).thenReturn(dto);
        when(categoriaModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/categorias/1", "self")));

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequestDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void guardar_datosInvalidos_debeRetornar400() throws Exception {
        CategoriaRequestDTO invalido = new CategoriaRequestDTO(
                "", "", null, true, null);

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_debeRetornarCategoriaActualizadaConLinks() throws Exception {
        CategoriaDTO dto = categoriaDTO();
        when(categoriaService.update(any(Integer.class), any())).thenReturn(dto);
        when(categoriaModelAssembler.toModel(dto)).thenReturn(
                EntityModel.of(dto, Link.of("/api/v1/categorias/1", "self")));

        mockMvc.perform(put("/api/v1/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequestDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void actualizar_noExistente_debeRetornar404() throws Exception {
        when(categoriaService.update(any(Integer.class), any()))
                .thenThrow(new ResourceNotFoundException("Categoria no encontrada"));

        mockMvc.perform(put("/api/v1/categorias/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequestDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_debeRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/categorias/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_noExistente_debeRetornar404() throws Exception {
        org.mockito.Mockito.doThrow(new ResourceNotFoundException("Categoria no encontrada"))
                .when(categoriaService).delete(99);

        mockMvc.perform(delete("/api/v1/categorias/99"))
                .andExpect(status().isNotFound());
    }
}

package com.arriendo.ms_reportes.controller;

import com.arriendo.ms_reportes.assembler.ReporteModelAssembler;
import com.arriendo.ms_reportes.dto.ReporteRequestDTO;
import com.arriendo.ms_reportes.dto.ReporteResponseDTO;
import com.arriendo.ms_reportes.exception.ResourceNotFoundException;
import com.arriendo.ms_reportes.model.Reporte;
import com.arriendo.ms_reportes.service.ReporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteController.class)
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReporteService reporteService;

    @MockitoBean
    private ReporteModelAssembler reporteModelAssembler;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Reporte crearReporte() {
        return new Reporte(
                1L,
                "Reporte Mensual",
                "Ingresos del mes",
                500000.0,
                3,
                LocalDate.of(2026, 6, 19)
        );
    }

    @Test
    void listar_CuandoExistenReportes_DebeRetornar200() throws Exception {
        Reporte reporte = crearReporte();

        when(reporteService.obtenerTodos()).thenReturn(List.of(reporte));
        when(reporteModelAssembler.toCollectionModel(any()))
                .thenAnswer(invocation -> org.springframework.hateoas.CollectionModel.of(
                        List.of(EntityModel.of(reporte))
                ));

        mockMvc.perform(get("/api/v1/reportes"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornar200() throws Exception {
        Reporte reporte = crearReporte();

        when(reporteService.obtenerPorId(1L)).thenReturn(reporte);
        when(reporteModelAssembler.toModel(reporte)).thenReturn(EntityModel.of(reporte));

        mockMvc.perform(get("/api/v1/reportes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoReporte").value("Reporte Mensual"));
    }

    @Test
    void buscarPorId_CuandoNoExiste_DebeRetornar404() throws Exception {
        when(reporteService.obtenerPorId(99L))
                .thenThrow(new ResourceNotFoundException("Reporte no encontrado"));

        mockMvc.perform(get("/api/v1/reportes/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Reporte no encontrado"));
    }

    @Test
    void guardar_CuandoDatosValidos_DebeRetornar200() throws Exception {
        ReporteRequestDTO requestDTO = new ReporteRequestDTO();
        requestDTO.setTipoReporte("Reporte Mensual");
        requestDTO.setDescripcion("Reporte de ingresos");
        requestDTO.setTotalIngresos(700000.0);
        requestDTO.setTotalReservas(4);
        requestDTO.setFechaGeneracion(LocalDate.of(2026, 6, 19));

        ReporteResponseDTO responseDTO = new ReporteResponseDTO(
                1L,
                "Reporte Mensual",
                "Reporte de ingresos",
                700000.0,
                4,
                LocalDate.of(2026, 6, 19)
        );

        when(reporteService.guardar(any(ReporteRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/reportes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoReporte").value("Reporte Mensual"));
    }
}

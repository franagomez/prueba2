package com.arriendo.ms_empleados.controller;

import com.arriendo.ms_empleados.assembler.EmpleadoModelAssembler;
import com.arriendo.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendo.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendo.ms_empleados.exception.ResourceNotFoundException;
import com.arriendo.ms_empleados.model.Empleado;
import com.arriendo.ms_empleados.service.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmpleadoService empleadoService;

    @MockitoBean
    private EmpleadoModelAssembler empleadoModelAssembler;

    private Empleado crearEmpleado() {
        return new Empleado(
                1L,
                "Francisca",
                "Gomez",
                "francisca.gomez@empresa.cl",
                850000.0,
                true,
                "Soporte TI",
                LocalDate.of(2025, 1, 10)
        );
    }

    @Test
    void listar_CuandoExistenEmpleados_DebeRetornar200() throws Exception {
        Empleado empleado = crearEmpleado();

        when(empleadoService.obtenerTodos()).thenReturn(List.of(empleado));
        when(empleadoModelAssembler.toCollectionModel(List.of(empleado)))
                .thenReturn(org.springframework.hateoas.CollectionModel.of(
                        List.of(EntityModel.of(empleado))));

        mockMvc.perform(get("/api/v1/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.empleadoList[0].nombre").value("Francisca"));
    }

    @Test
    void listar_CuandoNoHayEmpleados_DebeRetornar204() throws Exception {
        when(empleadoService.obtenerTodos()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/empleados"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornar200() throws Exception {
        Empleado empleado = crearEmpleado();

        when(empleadoService.obtenerPorId(1L)).thenReturn(empleado);
        when(empleadoModelAssembler.toModel(empleado)).thenReturn(EntityModel.of(empleado));

        mockMvc.perform(get("/api/v1/empleados/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Francisca"))
                .andExpect(jsonPath("$.email").value("francisca.gomez@empresa.cl"));
    }

    @Test
    void buscarPorId_CuandoNoExiste_DebeRetornar404() throws Exception {
        when(empleadoService.obtenerPorId(99L))
                .thenThrow(new ResourceNotFoundException("Empleado no encontrado"));

        mockMvc.perform(get("/api/v1/empleados/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Empleado no encontrado"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void guardar_CuandoDatosValidos_DebeRetornar200() throws Exception {
        EmpleadoRequestDTO dto = new EmpleadoRequestDTO();
        dto.setNombre("Francisca");
        dto.setApellido("Gomez");
        dto.setEmail("francisca.gomez@empresa.cl");
        dto.setSueldo(850000.0);
        dto.setActivo(true);
        dto.setCargo("Soporte TI");
        dto.setFechaContratacion(LocalDate.of(2025, 1, 10));

        EmpleadoResponseDTO responseDTO = new EmpleadoResponseDTO(
                1L, "Francisca", "Gomez", "francisca.gomez@empresa.cl",
                850000.0, true, "Soporte TI", LocalDate.of(2025, 1, 10)
        );

        when(empleadoService.guardar(any(EmpleadoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Francisca"));
    }

    @Test
    void guardar_CuandoDatosInvalidos_DebeRetornar400() throws Exception {
        EmpleadoRequestDTO dto = new EmpleadoRequestDTO();
        dto.setNombre("");
        dto.setApellido("Gomez");
        dto.setEmail("no-es-un-email");
        dto.setSueldo(-100.0);
        dto.setActivo(true);
        dto.setCargo("Soporte TI");
        dto.setFechaContratacion(LocalDate.of(2025, 1, 10));

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void actualizar_CuandoEmpleadoExiste_DebeRetornar200() throws Exception {
        EmpleadoRequestDTO dto = new EmpleadoRequestDTO();
        dto.setNombre("Francisca Actualizada");
        dto.setApellido("Gomez");
        dto.setEmail("francisca.gomez@empresa.cl");
        dto.setSueldo(900000.0);
        dto.setActivo(true);
        dto.setCargo("Soporte TI Senior");
        dto.setFechaContratacion(LocalDate.of(2025, 1, 10));

        Empleado actualizado = new Empleado(
                1L, "Francisca Actualizada", "Gomez", "francisca.gomez@empresa.cl",
                900000.0, true, "Soporte TI Senior", LocalDate.of(2025, 1, 10)
        );

        when(empleadoService.actualizar(anyLong(), any(EmpleadoRequestDTO.class))).thenReturn(actualizado);
        when(empleadoModelAssembler.toModel(actualizado)).thenReturn(EntityModel.of(actualizado));

        mockMvc.perform(put("/api/v1/empleados/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Francisca Actualizada"))
                .andExpect(jsonPath("$.cargo").value("Soporte TI Senior"));
    }

    @Test
    void actualizar_CuandoEmpleadoNoExiste_DebeRetornar404() throws Exception {
        EmpleadoRequestDTO dto = new EmpleadoRequestDTO();
        dto.setNombre("Francisca");
        dto.setApellido("Gomez");
        dto.setEmail("francisca.gomez@empresa.cl");
        dto.setSueldo(900000.0);
        dto.setActivo(true);
        dto.setCargo("Soporte TI");
        dto.setFechaContratacion(LocalDate.of(2025, 1, 10));

        when(empleadoService.actualizar(anyLong(), any(EmpleadoRequestDTO.class)))
                .thenThrow(new ResourceNotFoundException("Empleado no encontrado"));

        mockMvc.perform(put("/api/v1/empleados/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_CuandoEmpleadoExiste_DebeRetornar204() throws Exception {
        mockMvc.perform(delete("/api/v1/empleados/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}

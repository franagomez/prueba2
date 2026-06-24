package com.arriendo.ms_reportes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.arriendo.ms_reportes.dto.ReporteRequestDTO;
import com.arriendo.ms_reportes.dto.ReporteResponseDTO;
import com.arriendo.ms_reportes.model.Reporte;
import com.arriendo.ms_reportes.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(
        name = "Reportes",
        description = "API para la gestión de reportes y consulta de información relacionada con pagos y reservas."
)
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    //Listar reportes
    @Operation(summary = "Listar reportes", description = "Obtiene todos los reportes registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reportes encontrados"),
            @ApiResponse(responseCode = "204", description = "No existen reportes registrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Reporte>> listar() {

        List<Reporte> reportes = reporteService.obtenerTodos();

        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reportes);
    }

    //Obtener pagos
    @Operation(summary = "Obtener pagos", description = "Consulta la información de pagos desde el microservicio de pagos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagos obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen pagos registrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/pagos")
    public ResponseEntity<List<Map<String, Object>>> obtenerPagos() {

        List<Map<String, Object>> pagos = reporteService.obtenerPagos();

        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pagos);
    }


    // Obtener reservas
    @Operation(summary = "Obtener reservas", description = "Consulta la información de reservas desde el microservicio de reservas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen reservas registradas", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/reservas")
    public ResponseEntity<List<Map<String, Object>>> obtenerReservas() {

        List<Map<String, Object>> reservas = reporteService.obtenerReservas();

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    //Buscar por ID
    @Operation(summary = "Buscar reporte por ID", description = "Obtiene un reporte específico según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reporte> buscarPorId(
            @Parameter(description = "ID del reporte a buscar", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

    //Registrar reporte
    @Operation(summary = "Registrar reporte", description = "Registra un nuevo reporte en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReporteResponseDTO> guardar(
            @Valid @RequestBody ReporteRequestDTO dto) {

        return ResponseEntity.ok(reporteService.guardar(dto));
    }

    // Actualizar reporte por ID
    @Operation(summary = "Actualizar reporte", description = "Actualiza la información de un reporte existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizar(
            @Parameter(description = "ID del reporte a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ReporteRequestDTO dto) {

        return ResponseEntity.ok(reporteService.actualizar(id, dto));
    }

    //Eliminar reporte
    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte del sistema según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del reporte a eliminar", example = "1")
            @PathVariable Long id) {

        reporteService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
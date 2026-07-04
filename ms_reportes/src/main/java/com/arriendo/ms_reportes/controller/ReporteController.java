package com.arriendo.ms_reportes.controller;

import com.arriendo.ms_reportes.assembler.ReporteModelAssembler;
import com.arriendo.ms_reportes.dto.ReporteRequestDTO;
import com.arriendo.ms_reportes.dto.ReporteResponseDTO;
import com.arriendo.ms_reportes.model.Reporte;
import com.arriendo.ms_reportes.service.ReporteService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "API para la gestión de reportes y consulta de pagos y reservas.")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteModelAssembler reporteModelAssembler;

    @Operation(summary = "Listar reportes")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Reporte>>> listar() {
        List<Reporte> reportes = reporteService.obtenerTodos();

        if (reportes.isEmpty()) return ResponseEntity.noContent().build();

        CollectionModel<EntityModel<Reporte>> reportesConLinks = reporteModelAssembler.toCollectionModel(reportes);
        reportesConLinks.add(linkTo(methodOn(ReporteController.class).listar()).withSelfRel());

        return ResponseEntity.ok(reportesConLinks);
    }

    @Operation(summary = "Obtener pagos")
    @GetMapping("/pagos")
    public ResponseEntity<List<Map<String, Object>>> obtenerPagos() {
        List<Map<String, Object>> pagos = reporteService.obtenerPagos();
        if (pagos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(pagos);
    }

    @Operation(summary = "Obtener reservas")
    @GetMapping("/reservas")
    public ResponseEntity<List<Map<String, Object>>> obtenerReservas() {
        List<Map<String, Object>> reservas = reporteService.obtenerReservas();
        if (reservas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Buscar reporte por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Reporte>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteModelAssembler.toModel(reporteService.obtenerPorId(id)));
    }

    @Operation(summary = "Registrar reporte")
    @PostMapping
    public ResponseEntity<ReporteResponseDTO> guardar(@Valid @RequestBody ReporteRequestDTO dto) {
        return ResponseEntity.ok(reporteService.guardar(dto));
    }

    @Operation(summary = "Actualizar reporte")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Reporte>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReporteRequestDTO dto) {

        return ResponseEntity.ok(reporteModelAssembler.toModel(reporteService.actualizar(id, dto)));
    }

    @Operation(summary = "Eliminar reporte")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
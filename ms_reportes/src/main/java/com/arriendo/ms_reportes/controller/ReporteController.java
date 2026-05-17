package com.arriendo.ms_reportes.controller;

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
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<Reporte>> listar() {

        return ResponseEntity.ok(reporteService.obtenerTodos());
    }

    @GetMapping("/pagos")
    public ResponseEntity<List<Map<String, Object>>> obtenerPagos() {

        return ResponseEntity.ok(reporteService.obtenerPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReporteResponseDTO> guardar(
            @Valid @RequestBody ReporteRequestDTO dto) {

        return ResponseEntity.ok(reporteService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReporteRequestDTO dto) {

        return ResponseEntity.ok(reporteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        reporteService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
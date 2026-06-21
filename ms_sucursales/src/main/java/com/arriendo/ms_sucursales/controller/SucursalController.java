package com.arriendo.ms_sucursales.controller;

import com.arriendo.ms_sucursales.dto.SucursalRequestDTO;
import com.arriendo.ms_sucursales.dto.SucursalResponseDTO;
import com.arriendo.ms_sucursales.model.Sucursal;
import com.arriendo.ms_sucursales.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<Sucursal>> listar() {
        return ResponseEntity.ok(sucursalService.obtenerTodas());
    }

    @GetMapping("/operativas")
    public ResponseEntity<List<Sucursal>> buscarOperativas() {
        return ResponseEntity.ok(sucursalService.buscarOperativas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<SucursalResponseDTO> guardar(
            @Valid @RequestBody SucursalRequestDTO dto) {
        return ResponseEntity.ok(sucursalService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody SucursalRequestDTO dto) {
        return ResponseEntity.ok(sucursalService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
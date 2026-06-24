package com.arriendo.ms_sucursales.controller;

import com.arriendo.ms_sucursales.dto.RegionRequestDTO;
import com.arriendo.ms_sucursales.dto.RegionResponseDTO;
import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.service.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> listar() {
        return ResponseEntity.ok(regionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RegionResponseDTO> guardar(@Valid @RequestBody RegionRequestDTO dto) {
        return ResponseEntity.ok(regionService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody RegionRequestDTO dto) {

        return ResponseEntity.ok(regionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        regionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

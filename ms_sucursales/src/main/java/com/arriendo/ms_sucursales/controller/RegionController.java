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
@RequestMapping("/api/regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> listar() {
        return ResponseEntity.ok(regionService.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<RegionResponseDTO> guardar(@Valid @RequestBody RegionRequestDTO dto) {

        RegionResponseDTO response = regionService.guardar(dto);

        return ResponseEntity.ok(response);
    }
}

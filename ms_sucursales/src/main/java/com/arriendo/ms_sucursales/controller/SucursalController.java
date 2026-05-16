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
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<Sucursal>> listar() {
        return ResponseEntity.ok(sucursalService.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<SucursalResponseDTO> guardar(@Valid @RequestBody SucursalRequestDTO dto) {

        SucursalResponseDTO response = sucursalService.guardar(dto);

        return ResponseEntity.ok(response);
    }
}

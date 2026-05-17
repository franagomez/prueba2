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

    @PostMapping
    public ResponseEntity<SucursalResponseDTO> guardar(
            @Valid @RequestBody SucursalRequestDTO dto) {

        return ResponseEntity.ok(sucursalService.guardar(dto));
    }
}

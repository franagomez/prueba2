package com.arriendo.ms_pagos.controller;

import com.arriendo.ms_pagos.dto.PagoRequestDTO;
import com.arriendo.ms_pagos.dto.PagoResponseDTO;
import com.arriendo.ms_pagos.model.Pago;
import com.arriendo.ms_pagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(
        name = "Controlador de Pagos",
        description = "Operaciones para la gestión de pagos del sistema de arriendo"
)
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(
            summary = "Obtener todos los pagos",
            description = "Retorna una lista con todos los pagos registrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @Operation(
            summary = "Buscar pagos por rango",
            description = "Busca pagos entre un monto mínimo y máximo"
    )
    @ApiResponse(responseCode = "200", description = "Pagos encontrados correctamente")
    @GetMapping("/rango")
    public ResponseEntity<List<Pago>> buscarPorRango(
            @RequestParam Double minimo,
            @RequestParam Double maximo) {

        return ResponseEntity.ok(
                pagoService.buscarPorRango(minimo, maximo));
    }

    @Operation(
            summary = "Buscar pago por ID",
            description = "Obtiene un pago específico según su identificador"
    )
    @ApiResponse(responseCode = "200", description = "Pago encontrado correctamente")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @Operation(
            summary = "Registrar pago",
            description = "Crea un nuevo pago en el sistema"
    )
    @ApiResponse(responseCode = "201", description = "Pago creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ResponseEntity<PagoResponseDTO> guardar(@Valid @RequestBody PagoRequestDTO dto) {
        PagoResponseDTO response = pagoService.guardar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(
            summary = "Actualizar pago",
            description = "Actualiza la información de un pago existente"
    )
    @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PagoRequestDTO dto) {

        return ResponseEntity.ok(pagoService.actualizar(id, dto));
    }

    @Operation(
            summary = "Eliminar pago",
            description = "Elimina un pago por su identificador"
    )
    @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
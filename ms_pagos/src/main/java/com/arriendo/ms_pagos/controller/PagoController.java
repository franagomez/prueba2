package com.arriendo.ms_pagos.controller;

import com.arriendo.ms_pagos.dto.PagoRequestDTO;
import com.arriendo.ms_pagos.dto.PagoResponseDTO;
import com.arriendo.ms_pagos.model.Pago;
import com.arriendo.ms_pagos.service.PagoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    //Listar pagos
    @Operation(
            summary = "Obtener todos los pagos",
            description = "Retorna una lista con todos los pagos registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    //Buscar pagos
    @Operation(
            summary = "Buscar pagos por rango",
            description = "Busca pagos entre un monto mínimo y máximo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagos encontrados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/rango")
    public ResponseEntity<List<Pago>> buscarPorRango(
            @Parameter(description = "Monto mínimo del rango", example = "10000")
            @RequestParam Double minimo,
            @Parameter(description = "Monto máximo del rango", example = "100000")
            @RequestParam Double maximo) {

        return ResponseEntity.ok(
                pagoService.buscarPorRango(minimo, maximo));
    }

    //Buscar por ID
    @Operation(
            summary = "Buscar pago por ID",
            description = "Obtiene un pago específico según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPorId(
            @Parameter(description = "ID del pago", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    //Crear pago en el sistema
    @Operation(
            summary = "Registrar pago",
            description = "Crea un nuevo pago en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PagoResponseDTO> guardar(@Valid @RequestBody PagoRequestDTO dto) {
        PagoResponseDTO response = pagoService.guardar(dto);
        return ResponseEntity.status(201).body(response);
    }

    //Actualizar pago en el sistema
    @Operation(
            summary = "Actualizar pago",
            description = "Actualiza la información de un pago existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(
            @Parameter(description = "ID del pago a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PagoRequestDTO dto) {

        return ResponseEntity.ok(pagoService.actualizar(id, dto));
    }

    //Eliminar pago
    @Operation(
            summary = "Eliminar pago",
            description = "Elimina un pago por su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pago a eliminar", example = "1")
            @PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
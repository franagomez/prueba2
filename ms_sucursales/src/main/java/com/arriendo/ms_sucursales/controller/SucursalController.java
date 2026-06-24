package com.arriendo.ms_sucursales.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Sucursales", description = "Operaciones CRUD y consultas de sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    //Listar sucursales
    @Operation(
            summary = "Listar sucursales",
            description = "Obtiene todas las sucursales registradas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursales encontradas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Sucursal>> listar() {

        return ResponseEntity.ok(sucursalService.obtenerTodas());
    }

    //Sucursales operativas
    @Operation(
            summary = "Buscar sucursales operativas",
            description = "Obtiene las sucursales que se encuentran operativas, ordenadas alfabéticamente por nombre."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursales operativas encontradas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/operativas")
    public ResponseEntity<List<Sucursal>> buscarOperativas() {

        return ResponseEntity.ok(sucursalService.buscarOperativas());
    }

    //Buscar sucursal por ID
    @Operation(
            summary = "Buscar sucursal por ID",
            description = "Obtiene una sucursal específica según su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> buscarPorId(
            @Parameter(description = "ID de la sucursal", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    //Crear sucursal
    @Operation(
            summary = "Crear sucursal",
            description = "Registra una nueva sucursal en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucursal creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SucursalResponseDTO> guardar(
            @Valid @RequestBody SucursalRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sucursalService.guardar(dto));
    }

    //Actualizar sucursal
    @Operation(
            summary = "Actualizar sucursal",
            description = "Actualiza los datos de una sucursal existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(
            @Parameter(description = "ID de la sucursal a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody SucursalRequestDTO dto) {

        return ResponseEntity.ok(sucursalService.actualizar(id, dto));
    }

    //Eliminar sucursal
    @Operation(
            summary = "Eliminar sucursal",
            description = "Elimina una sucursal existente según su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucursal eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la sucursal a eliminar", example = "1")
            @PathVariable Long id) {

        sucursalService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}

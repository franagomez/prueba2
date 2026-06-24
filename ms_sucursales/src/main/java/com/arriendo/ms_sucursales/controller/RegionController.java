package com.arriendo.ms_sucursales.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Regiones", description = "Operaciones CRUD de regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    //Listar regiones
    @Operation(summary = "Listar regiones", description = "Obtiene todas las regiones registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regiones encontradas"),
            @ApiResponse(responseCode = "204", description = "No existen regiones registradas", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Region>> listar() {
        List<Region> regiones = regionService.obtenerTodas();

        if(regiones.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(regiones);
    }

    //Buscar región por ID
    @Operation(summary = "Buscar región por ID", description = "Obtiene una región específica según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Región encontrada"),
            @ApiResponse(responseCode = "404", description = "Región no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Region> buscarPorId(
            @Parameter(description = "ID de la región", example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(regionService.obtenerPorId(id));
    }

    //Crear/registrar nueva region en el sistema
    @Operation(summary = "Crear región", description = "Registra una nueva región en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Región creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RegionResponseDTO> guardar(@Valid @RequestBody RegionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(regionService.guardar(dto));
    }

    //Actualizar región
    @Operation(summary = "Actualizar región", description = "Actualiza los datos de una región existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Región actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Región no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Region> actualizar(
            @Parameter(description = "ID de la región a actualizar", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody RegionRequestDTO dto) {

        return ResponseEntity.ok(regionService.actualizar(id, dto));
    }

    //Eliminar región registrada
    @Operation(summary = "Eliminar región", description = "Elimina una región existente según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Región eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Región no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la región a eliminar", example = "1")
            @PathVariable Integer id) {
        regionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

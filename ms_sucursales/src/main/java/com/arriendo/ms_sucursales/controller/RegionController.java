package com.arriendo.ms_sucursales.controller;

import com.arriendo.ms_sucursales.assembler.RegionModelAssembler;
import com.arriendo.ms_sucursales.dto.RegionRequestDTO;
import com.arriendo.ms_sucursales.dto.RegionResponseDTO;
import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/regiones")
@Tag(name = "Regiones", description = "Operaciones CRUD de regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionModelAssembler regionModelAssembler;

    @Operation(summary = "Listar regiones", description = "Obtiene todas las regiones registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regiones encontradas"),
            @ApiResponse(responseCode = "204", description = "No existen regiones registradas", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Region>>> listar() {
        List<Region> regiones = regionService.obtenerTodas();

        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<Region>> respuesta =
                regionModelAssembler.toCollectionModel(regiones)
                        .add(linkTo(methodOn(RegionController.class).listar()).withSelfRel());

        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar región por ID", description = "Obtiene una región específica según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Región encontrada"),
            @ApiResponse(responseCode = "404", description = "Región no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Region>> buscarPorId(
            @Parameter(description = "ID de la región", example = "1")
            @PathVariable Integer id) {

        Region region = regionService.obtenerPorId(id);
        return ResponseEntity.ok(regionModelAssembler.toModel(region));
    }

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

    @Operation(summary = "Actualizar región", description = "Actualiza los datos de una región existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Región actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Región no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Region>> actualizar(
            @Parameter(description = "ID de la región a actualizar", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody RegionRequestDTO dto) {

        Region region = regionService.actualizar(id, dto);
        return ResponseEntity.ok(regionModelAssembler.toModel(region));
    }

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
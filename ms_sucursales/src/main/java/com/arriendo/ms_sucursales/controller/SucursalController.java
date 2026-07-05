package com.arriendo.ms_sucursales.controller;

import com.arriendo.ms_sucursales.assembler.SucursalModelAssembler;
import com.arriendo.ms_sucursales.dto.SucursalRequestDTO;
import com.arriendo.ms_sucursales.dto.SucursalResponseDTO;
import com.arriendo.ms_sucursales.model.Sucursal;
import com.arriendo.ms_sucursales.service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/sucursales")
@Tag(name = "Sucursales", description = "Operaciones CRUD y consultas de sucursales")
public class SucursalController {

    private final SucursalService sucursalService;
    private final SucursalModelAssembler sucursalModelAssembler;

    public SucursalController(SucursalService sucursalService, SucursalModelAssembler sucursalModelAssembler) {
        this.sucursalService = sucursalService;
        this.sucursalModelAssembler = sucursalModelAssembler;
    }

    @Operation(summary = "Listar sucursales", description = "Obtiene todas las sucursales registradas.")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> listar() {

        CollectionModel<EntityModel<Sucursal>> respuesta =
                sucursalModelAssembler.toCollectionModel(sucursalService.obtenerTodas())
                        .add(linkTo(methodOn(SucursalController.class).listar()).withSelfRel());

        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar sucursales operativas")
    @GetMapping("/operativas")
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> buscarOperativas() {

        CollectionModel<EntityModel<Sucursal>> respuesta =
                sucursalModelAssembler.toCollectionModel(sucursalService.buscarOperativas())
                        .add(linkTo(methodOn(SucursalController.class).buscarOperativas()).withSelfRel());

        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar sucursal por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> buscarPorId(
            @Parameter(description = "ID de la sucursal")
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                sucursalModelAssembler.toModel(sucursalService.obtenerPorId(id))
        );
    }

    @Operation(summary = "Crear sucursal")
    @PostMapping
    public ResponseEntity<SucursalResponseDTO> guardar(
            @Valid @RequestBody SucursalRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sucursalService.guardar(dto));
    }

    @Operation(summary = "Actualizar sucursal")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody SucursalRequestDTO dto) {

        return ResponseEntity.ok(
                sucursalModelAssembler.toModel(sucursalService.actualizar(id, dto))
        );
    }

    @Operation(summary = "Eliminar sucursal")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
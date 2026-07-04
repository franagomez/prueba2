package com.arriendo.ms_empleados.controller;

import com.arriendo.ms_empleados.assembler.EmpleadoModelAssembler;
import com.arriendo.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendo.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendo.ms_empleados.model.Empleado;
import com.arriendo.ms_empleados.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/empleados")
@Tag(name = "Empleados", description = "API para la gestión de empleados del sistema de arriendo de vehículos.")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private EmpleadoModelAssembler empleadoModelAssembler;

    @Operation(summary = "Listar empleados", description = "Obtiene todos los empleados registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleados encontrados"),
            @ApiResponse(responseCode = "204", description = "No existen empleados registrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Empleado>>> listar() {

        List<Empleado> empleados = empleadoService.obtenerTodos();

        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<Empleado>> respuesta = empleadoModelAssembler.toCollectionModel(empleados);
        respuesta.add(linkTo(methodOn(EmpleadoController.class).listar()).withSelfRel());

        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar empleados activos por año",
            description = "Obtiene los empleados activos contratados durante un año determinado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleados encontrados"),
            @ApiResponse(responseCode = "204", description = "No existen empleados activos para el año indicado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/activos")
    public ResponseEntity<CollectionModel<EntityModel<Empleado>>> buscarActivosPorAnio(
            @Parameter(description = "Año de contratación", example = "2024")
            @RequestParam Integer anio) {

        List<Empleado> empleados = empleadoService.buscarActivosPorAnio(anio);

        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<Empleado>> respuesta = empleadoModelAssembler.toCollectionModel(empleados);
        respuesta.add(linkTo(methodOn(EmpleadoController.class).buscarActivosPorAnio(anio)).withSelfRel());
        respuesta.add(linkTo(methodOn(EmpleadoController.class).listar()).withRel("todos-los-empleados"));

        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar empleado por ID", description = "Obtiene un empleado según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Empleado>> buscarPorId(
            @Parameter(description = "ID del empleado a buscar", example = "1")
            @PathVariable Long id) {

        Empleado empleado = empleadoService.obtenerPorId(id);
        return ResponseEntity.ok(empleadoModelAssembler.toModel(empleado));
    }

    @Operation(summary = "Registrar empleado", description = "Registra un nuevo empleado en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> guardar(@Valid @RequestBody EmpleadoRequestDTO dto) {
        EmpleadoResponseDTO response = empleadoService.guardar(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar empleado", description = "Actualiza la información de un empleado existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Empleado>> actualizar(
            @Parameter(description = "ID del empleado a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoRequestDTO dto) {

        Empleado empleado = empleadoService.actualizar(id, dto);
        return ResponseEntity.ok(empleadoModelAssembler.toModel(empleado));
    }

    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado del sistema según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empleado eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Eliminar empleado por ID", example = "1")
            @PathVariable Long id) {

        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
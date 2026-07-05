package com.msreservas.ms_reservas.Controller;

import com.msreservas.ms_reservas.Assembler.EstadoReservaModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.msreservas.ms_reservas.DTO.EstadoReservaDTO;
import com.msreservas.ms_reservas.DTO.EstadoReservaRequestDTO;
import com.msreservas.ms_reservas.Service.EstadoReservaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/v1/estados-reserva")
@Tag(name = "Estados de Reserva", description = "Operaciones CRUD de estados de reserva")
public class EstadoReservaController {

    private final EstadoReservaService estadoReservaService;
    private final EstadoReservaModelAssembler estadoReservaModelAssembler;

    public EstadoReservaController(EstadoReservaService estadoReservaService,
                                    EstadoReservaModelAssembler estadoReservaModelAssembler) {
        this.estadoReservaService = estadoReservaService;
        this.estadoReservaModelAssembler = estadoReservaModelAssembler;
    }

    @Operation(summary = "Listar estados de reserva", description = "Obtiene todos los estados de reserva registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estados encontrados"),
            @ApiResponse(responseCode = "204", description = "No existen estados registrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EstadoReservaDTO>>> listar() {
        log.info("GET /api/v1/estados-reserva");
        List<EstadoReservaDTO> estados = estadoReservaService.findAll();

        if (estados.isEmpty()) {
            log.warn("No se encontraron estados de reserva registrados");
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<EstadoReservaDTO>> estadosConLinks = estados.stream()
                .map(estadoReservaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(estadosConLinks,
                linkTo(methodOn(EstadoReservaController.class).listar()).withSelfRel()));
    }

    @Operation(summary = "Buscar estado de reserva por ID", description = "Obtiene un estado de reserva específico según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EstadoReservaDTO>> buscarPorId(
            @Parameter(description = "ID del estado de reserva", example = "1")
            @PathVariable Integer id) {
        log.info("GET /api/v1/estados-reserva/{}", id);
        EstadoReservaDTO estado = estadoReservaService.findById(id);
        return ResponseEntity.ok(estadoReservaModelAssembler.toModel(estado));
    }

    @Operation(summary = "Crear estado de reserva", description = "Registra un nuevo estado de reserva.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<EstadoReservaDTO>> guardar(@Valid @RequestBody EstadoReservaRequestDTO dto) {
        log.info("POST /api/v1/estados-reserva con nombre {}", dto.getNombre());
        EstadoReservaDTO estado = estadoReservaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoReservaModelAssembler.toModel(estado));
    }

    @Operation(summary = "Actualizar estado de reserva", description = "Actualiza un estado de reserva existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EstadoReservaDTO>> actualizar(
            @Parameter(description = "ID del estado de reserva a actualizar", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody EstadoReservaRequestDTO dto) {
        log.info("PUT /api/v1/estados-reserva/{}", id);
        EstadoReservaDTO estado = estadoReservaService.update(id, dto);
        return ResponseEntity.ok(estadoReservaModelAssembler.toModel(estado));
    }

    @Operation(summary = "Eliminar estado de reserva", description = "Elimina un estado de reserva existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estado eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del estado de reserva a eliminar", example = "1")
            @PathVariable Integer id) {
        log.info("DELETE /api/v1/estados-reserva/{}", id);
        estadoReservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

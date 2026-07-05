package com.msclientes.ms_clientes.Controller;

import com.msclientes.ms_clientes.Assembler.DireccionModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.msclientes.ms_clientes.DTO.DireccionDTO;
import com.msclientes.ms_clientes.DTO.DireccionRequestDTO;
import com.msclientes.ms_clientes.Service.DireccionService;
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
@RequestMapping("/api/v1/direcciones")
@Tag(name = "Direcciones", description = "Operaciones CRUD de direcciones asociadas a clientes")
public class DireccionController {

    private final DireccionService direccionService;
    private final DireccionModelAssembler direccionModelAssembler;

    public DireccionController(DireccionService direccionService,
                               DireccionModelAssembler direccionModelAssembler) {
        this.direccionService = direccionService;
        this.direccionModelAssembler = direccionModelAssembler;
    }

    @Operation(summary = "Listar direcciones", description = "Obtiene todas las direcciones registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Direcciones encontradas correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DireccionDTO>>> listar() {
        log.info("GET /api/v1/direcciones");

        List<DireccionDTO> direcciones = direccionService.findAll();

        if (direcciones.isEmpty()) {
            log.warn("No se encontraron direcciones registradas");
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<DireccionDTO>> direccionesConLinks = direcciones.stream()
                .map(direccionModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(direccionesConLinks,
                linkTo(methodOn(DireccionController.class).listar()).withSelfRel()));
    }

    @Operation(summary = "Buscar dirección por ID", description = "Obtiene una dirección específica mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dirección encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DireccionDTO>> buscarPorId(
            @Parameter(description = "ID de la dirección", example = "1")
            @PathVariable Integer id) {

        log.info("GET /api/v1/direcciones/{}", id);

        DireccionDTO direccion = direccionService.findById(id);
        return ResponseEntity.ok(direccionModelAssembler.toModel(direccion));
    }

    @Operation(summary = "Crear dirección", description = "Registra una nueva dirección asociada a un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dirección creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente asociado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<DireccionDTO>> guardar(@Valid @RequestBody DireccionRequestDTO dto) {
        log.info("POST /api/v1/direcciones para cliente id: {}", dto.getClienteId());

        DireccionDTO creada = direccionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionModelAssembler.toModel(creada));
    }

    @Operation(summary = "Actualizar dirección", description = "Actualiza los datos de una dirección existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dirección actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dirección o cliente asociado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DireccionDTO>> actualizar(
            @Parameter(description = "ID de la dirección", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody DireccionRequestDTO dto) {

        log.info("PUT /api/v1/direcciones/{}", id);

        DireccionDTO actualizada = direccionService.update(id, dto);
        return ResponseEntity.ok(direccionModelAssembler.toModel(actualizada));
    }

    @Operation(summary = "Eliminar dirección", description = "Elimina una dirección existente mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dirección eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la dirección", example = "1")
            @PathVariable Integer id) {

        log.info("DELETE /api/v1/direcciones/{}", id);

        direccionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
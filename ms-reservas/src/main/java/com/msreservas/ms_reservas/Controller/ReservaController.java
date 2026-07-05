package com.msreservas.ms_reservas.Controller;

import com.msreservas.ms_reservas.Assembler.ReservaModelAssembler;
import com.msreservas.ms_reservas.DTO.ReservaDTO;
import com.msreservas.ms_reservas.DTO.ReservaRequestDTO;
import com.msreservas.ms_reservas.Service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/reservas")
@Tag(name = "Reservas", description = "Operaciones CRUD y consultas de reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaModelAssembler reservaModelAssembler;

    public ReservaController(ReservaService reservaService, ReservaModelAssembler reservaModelAssembler) {
        this.reservaService = reservaService;
        this.reservaModelAssembler = reservaModelAssembler;
    }

    @Operation(summary = "Listar reservas", description = "Obtiene todas las reservas registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas"),
            @ApiResponse(responseCode = "204", description = "No existen reservas registradas", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> listar() {
        log.info("GET /api/v1/reservas");
        List<ReservaDTO> reservas = reservaService.findAll();

        if (reservas.isEmpty()) {
            log.warn("No se encontraron reservas registradas");
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<ReservaDTO>> reservasConLinks = reservas.stream()
                .map(reservaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(reservasConLinks,
                linkTo(methodOn(ReservaController.class).listar()).withSelfRel()));
    }

    @Operation(summary = "Buscar reserva por ID", description = "Obtiene una reserva específica según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReservaDTO>> buscarPorId(
            @Parameter(description = "ID de la reserva a buscar", example = "1")
            @PathVariable Integer id) {
        log.info("GET /api/v1/reservas/{}", id);
        ReservaDTO reserva = reservaService.findById(id);
        return ResponseEntity.ok(reservaModelAssembler.toModel(reserva));
    }

    @Operation(summary = "Crear reserva", description = "Registra una nueva reserva en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<ReservaDTO>> guardar(@Valid @RequestBody ReservaRequestDTO dto) {
        log.info("POST /api/v1/reservas para cliente id {}", dto.getClienteId());
        ReservaDTO reservaGuardada = reservaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaModelAssembler.toModel(reservaGuardada));
    }

    @Operation(summary = "Actualizar reserva", description = "Actualiza los datos de una reserva existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ReservaDTO>> actualizar(
            @Parameter(description = "ID de la reserva a actualizar", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody ReservaRequestDTO dto) {
        log.info("PUT /api/v1/reservas/{}", id);
        ReservaDTO reservaActualizada = reservaService.update(id, dto);
        return ResponseEntity.ok(reservaModelAssembler.toModel(reservaActualizada));
    }

    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la reserva a eliminar", example = "1")
            @PathVariable Integer id) {
        log.info("DELETE /api/v1/reservas/{}", id);
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar reservas activas por duración mayor",
            description = "Obtiene reservas activas cuya duración en días sea mayor al valor indicado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas activas encontradas"),
            @ApiResponse(responseCode = "204", description = "No existen reservas activas con duración mayor al valor indicado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/activas/dias-mayor/{dias}")
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> buscarActivasPorDiasMayor(
            @Parameter(description = "Cantidad mínima de días de duración", example = "3")
            @PathVariable Integer dias) {
        log.info("GET /api/v1/reservas/activas/dias-mayor/{}", dias);
        List<ReservaDTO> reservas = reservaService.buscarActivasPorDiasMayor(dias);

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<ReservaDTO>> reservasConLinks = reservas.stream()
                .map(reservaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(reservasConLinks,
                linkTo(methodOn(ReservaController.class).buscarActivasPorDiasMayor(dias)).withSelfRel(),
                linkTo(methodOn(ReservaController.class).listar()).withRel("todas-las-reservas")));
    }

    @Operation(summary = "Buscar reservas desde una fecha",
            description = "Obtiene reservas cuya fecha de inicio sea igual o posterior a la fecha indicada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas desde la fecha indicada"),
            @ApiResponse(responseCode = "204", description = "No existen reservas desde la fecha indicada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/desde-fecha/{fecha}")
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> buscarDesdeFecha(
            @Parameter(description = "Fecha inicial de búsqueda en formato yyyy-mm-dd", example = "2026-03-15")
            @PathVariable LocalDate fecha) {
        log.info("GET /api/v1/reservas/desde-fecha/{}", fecha);
        List<ReservaDTO> reservas = reservaService.buscarDesdeFecha(fecha);

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<ReservaDTO>> reservasConLinks = reservas.stream()
                .map(reservaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(reservasConLinks,
                linkTo(methodOn(ReservaController.class).buscarDesdeFecha(fecha)).withSelfRel(),
                linkTo(methodOn(ReservaController.class).listar()).withRel("todas-las-reservas")));
    }
}

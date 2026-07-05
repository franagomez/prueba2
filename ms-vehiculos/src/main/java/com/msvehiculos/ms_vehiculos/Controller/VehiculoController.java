package com.msvehiculos.ms_vehiculos.Controller;

import com.msvehiculos.ms_vehiculos.Assembler.VehiculoModelAssembler;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoDTO;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoRequestDTO;
import com.msvehiculos.ms_vehiculos.Service.VehiculoService;
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

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/vehiculos")
@Tag(name = "Vehículos", description = "Operaciones CRUD y consultas de vehículos")
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final VehiculoModelAssembler vehiculoModelAssembler;

    public VehiculoController(VehiculoService vehiculoService, VehiculoModelAssembler vehiculoModelAssembler) {
        this.vehiculoService = vehiculoService;
        this.vehiculoModelAssembler = vehiculoModelAssembler;
    }

    @Operation(summary = "Listar vehículos", description = "Obtiene todos los vehículos registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículos encontrados"),
            @ApiResponse(responseCode = "204", description = "No existen vehículos registrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<VehiculoDTO>>> listar() {
        log.info("GET /api/v1/vehiculos");
        List<VehiculoDTO> vehiculos = vehiculoService.findAll();

        if (vehiculos.isEmpty()) {
            log.warn("No se encontraron vehículos registrados");
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<VehiculoDTO>> vehiculosConLinks = vehiculos.stream()
                .map(vehiculoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(vehiculosConLinks,
                linkTo(methodOn(VehiculoController.class).listar()).withSelfRel()));
    }

    @Operation(summary = "Buscar vehículos por ID", description = "Obtiene un vehículo específico por medio del ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<VehiculoDTO>> buscarPorId(
            @Parameter(description = "ID del vehículo a buscar", example = "1")
            @PathVariable Integer id) {
        log.info("GET /api/v1/vehiculos/{}", id);
        VehiculoDTO vehiculo = vehiculoService.findById(id);
        return ResponseEntity.ok(vehiculoModelAssembler.toModel(vehiculo));
    }

    @Operation(summary = "Crear vehículo", description = "Registra un nuevo vehículo en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehículo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<VehiculoDTO>> guardar(@Valid @RequestBody VehiculoRequestDTO dto) {
        log.info("POST /api/v1/vehiculos con patente {}", dto.getPatente());
        VehiculoDTO vehiculo = vehiculoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoModelAssembler.toModel(vehiculo));
    }

    @Operation(summary = "Actualizar vehículo", description = "Actualiza los datos de un vehículo existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<VehiculoDTO>> actualizar(
            @Parameter(description = "ID del vehículo a actualizar", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody VehiculoRequestDTO dto) {
        log.info("PUT /api/v1/vehiculos/{}", id);
        VehiculoDTO vehiculo = vehiculoService.update(id, dto);
        return ResponseEntity.ok(vehiculoModelAssembler.toModel(vehiculo));
    }

    @Operation(summary = "Eliminar vehículo", description = "Elimina un vehículo existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehículo eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del vehículo a eliminar", example = "1")
            @PathVariable Integer id) {
        log.info("DELETE /api/v1/vehiculos/{}", id);
        vehiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar vehículos disponibles por precio menor",
            description = "Obtiene los vehículos disponibles cuyo precio diario sea menor al valor indicado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículos disponibles encontrados"),
            @ApiResponse(responseCode = "204", description = "No existen vehículos disponibles con precio menor al indicado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping("/disponibles/precio-menor/{precio}")
    public ResponseEntity<CollectionModel<EntityModel<VehiculoDTO>>> buscarDisponiblesPorPrecioMenor(
            @Parameter(description = "Precio máximo diario del vehículo", example = "35000")
            @PathVariable Double precio) {
        log.info("GET /api/v1/vehiculos/disponibles/precio-menor/{}", precio);
        List<VehiculoDTO> vehiculos = vehiculoService.buscarDisponiblesPorPrecioMenor(precio);

        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<VehiculoDTO>> vehiculosConLinks = vehiculos.stream()
                .map(vehiculoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(vehiculosConLinks,
                linkTo(methodOn(VehiculoController.class).buscarDisponiblesPorPrecioMenor(precio)).withSelfRel(),
                linkTo(methodOn(VehiculoController.class).listar()).withRel("todos-los-vehiculos")));
    }
}

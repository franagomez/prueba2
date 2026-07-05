package com.msclientes.ms_clientes.Controller;

import com.msclientes.ms_clientes.Assembler.ClienteModelAssembler;
import com.msclientes.ms_clientes.DTO.ClienteDTO;
import com.msclientes.ms_clientes.DTO.ClienteRequestDTO;
import com.msclientes.ms_clientes.Service.ClienteService;
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
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones CRUD del microservicio de clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteModelAssembler clienteModelAssembler;

    public ClienteController(ClienteService clienteService, ClienteModelAssembler clienteModelAssembler) {
        this.clienteService = clienteService;
        this.clienteModelAssembler = clienteModelAssembler;
    }

    @Operation(summary = "Listar clientes", description = "Obtiene todos los clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> listar() {
        log.info("GET /api/v1/clientes");

        List<ClienteDTO> clientes = clienteService.findAll();

        if (clientes.isEmpty()) {
            log.warn("No se encontraron clientes registrados");
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<ClienteDTO>> clientesConLinks = clientes.stream()
                .map(clienteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(clientesConLinks,
                linkTo(methodOn(ClienteController.class).listar()).withSelfRel()));
    }

    @Operation(summary = "Buscar cliente por ID", description = "Obtiene un cliente específico mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDTO>> buscarPorId(
            @Parameter(description = "ID del cliente", example = "1")
            @PathVariable Integer id) {

        log.info("GET /api/v1/clientes/{}", id);

        ClienteDTO cliente = clienteService.findById(id);

        return ResponseEntity.ok(clienteModelAssembler.toModel(cliente));
    }

    @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<ClienteDTO>> guardar(@Valid @RequestBody ClienteRequestDTO dto) {
        log.info("POST /api/v1/clientes");

        ClienteDTO clienteCreado = clienteService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteModelAssembler.toModel(clienteCreado));
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDTO>> actualizar(
            @Parameter(description = "ID cliente", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO dto) {

        log.info("PUT /api/v1/clientes/{}", id);

        ClienteDTO clienteActualizado = clienteService.update(id, dto);

        return ResponseEntity.ok(clienteModelAssembler.toModel(clienteActualizado));
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente existente mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del cliente", example = "1")
            @PathVariable Integer id) {

        log.info("DELETE /api/v1/clientes/{}", id);

        clienteService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
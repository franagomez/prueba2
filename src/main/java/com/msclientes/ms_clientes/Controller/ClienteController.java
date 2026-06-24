package com.msclientes.ms_clientes.Controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.msclientes.ms_clientes.DTO.ClienteDTO;
import com.msclientes.ms_clientes.DTO.ClienteRequestDTO;
import com.msclientes.ms_clientes.Service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones CRUD del microservicio de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //Listar clientes
    @Operation(summary = "Listar clientes", description = "Obtiene todos los clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar(){

        List<ClienteDTO> clientes = clienteService.findAll();

        if (clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    // Buscar cliente por ID
    @Operation(summary = "Buscar cliente por ID", description = "Obtiene un cliente específico mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(
            @Parameter(description = "ID del cliente", example = "1")
            @PathVariable Integer id){
        return ResponseEntity.ok(clienteService.findById(id));
    }

    // Crear cliente
    @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ClienteDTO> guardar(@Valid @RequestBody ClienteRequestDTO dto){

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(dto));
    }

    // Actualizar cliente
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(
            @Parameter(description = "ID cliente", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO dto) {

        return ResponseEntity.ok(clienteService.update(id, dto));
    }

    // Eliminar cliente
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente existente mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del cliente", example = "1")
            @PathVariable Integer id){

        boolean eliminado = clienteService.delete(id);

        if (!eliminado){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}

package com.msclientes.ms_clientes.Controller;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/direcciones")
@Tag(name = "Direcciones", description = "Operaciones CRUD de direcciones asociadas a clientes")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;


    // Listar todas las direcciones con GET
    @Operation(summary = "Listar direcciones", description = "Obtiene todas las direcciones registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Direcciones encontradas correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<DireccionDTO>> listar(){

        List<DireccionDTO> direcciones = direccionService.findAll();

        if (direcciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(direcciones);
    }

    // Buscar direccion por id
    @Operation(summary = "Buscar dirección por ID", description = "Obtiene una dirección específica mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dirección encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DireccionDTO> buscarPorId(
            @Parameter(description = "ID de la dirección", example = "1")
            @PathVariable Integer id){

        return ResponseEntity.ok(direccionService.findById(id));
    }

    // Crear direccion
    @Operation(summary = "Crear dirección", description = "Registra una nueva dirección asociada a un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dirección creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente asociado no encontrado",  content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PostMapping
    public ResponseEntity<DireccionDTO> guardar(@Valid @RequestBody DireccionRequestDTO dto){

        return ResponseEntity.status(HttpStatus.CREATED).body(direccionService.save(dto));
    }

    // Actualizar direccion
    @Operation(summary = "Actualizar dirección", description = "Actualiza los datos de una dirección existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dirección actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dirección o cliente asociado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> actualizar(
            @Parameter(description = "ID de la dirección", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody DireccionRequestDTO dto){

        return ResponseEntity.ok(direccionService.update(id, dto));
    }

    // Eliminar direccion
    @Operation(summary = "Eliminar dirección", description = "Elimina una dirección existente mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dirección eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la dirección", example = "1")
            @PathVariable Integer id){

        boolean eliminado = direccionService.delete(id);

        if (!eliminado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

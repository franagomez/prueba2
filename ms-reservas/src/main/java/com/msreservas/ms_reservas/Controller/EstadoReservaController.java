package com.msreservas.ms_reservas.Controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estados-reserva")
@Tag(name = "Estados de Reserva", description = "Operaciones CRUD de estados de reserva")
public class EstadoReservaController {

    @Autowired
    private EstadoReservaService estadoReservaService;

    // Listar estados de reserva
    @Operation(summary = "Listar estados de reserva", description = "Obtiene todos los estados de reserva registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estados encontrados"),
            @ApiResponse(responseCode = "204", description = "No existen estados registrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<EstadoReservaDTO>> listar(){
        List<EstadoReservaDTO> estados = estadoReservaService.findAll();

        if(estados.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(estados);
    }

    // Buscar por id
    @Operation(summary = "Buscar estado de reserva por ID", description = "Obtiene un estado de reserva específico según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstadoReservaDTO> buscarPorId(
            @Parameter(description = "ID del estado de reserva", example = "1")
            @PathVariable Integer id){
        return ResponseEntity.ok(estadoReservaService.findById(id));
    }

    //Crear estado de reserva
    @Operation(summary = "Crear estado de reserva", description = "Registra un nuevo estado de reserva.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EstadoReservaDTO> guardar(@Valid @RequestBody EstadoReservaRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoReservaService.save(dto));
    }

    // Actualizar estado de reserva
    @Operation(summary = "Actualizar estado de reserva", description = "Actualiza un estado de reserva existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstadoReservaDTO> actualizar(
            @Parameter(description = "ID del estado de reserva a actualizar", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody EstadoReservaRequestDTO dto){
        return ResponseEntity.ok(estadoReservaService.update(id, dto));
    }

    // Eliminar estado de reserva
    @Operation(summary = "Eliminar estado de reserva", description = "Elimina un estado de reserva existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estado eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del estado de reserva a eliminar", example = "1")
            @PathVariable Integer id){
        boolean eliminado = estadoReservaService.delete(id);

        if(!eliminado){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}

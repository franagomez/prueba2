package com.msvehiculos.ms_vehiculos.Controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoDTO;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoRequestDTO;
import com.msvehiculos.ms_vehiculos.Service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/vehiculos")
@Tag(name = "Vehículos", description = "Operaciones CRUD y consultas de vehículos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    // Listar vehiculos
    @Operation(summary = "Listar vehículos", description = "Obtiene todos los vehículos registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículos encontrados"),
            @ApiResponse(responseCode = "204", description = "No existen vehículos registrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> listar(){

        List<VehiculoDTO> vehiculos = vehiculoService.findAll();

        if (vehiculos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(vehiculos);
    }

    // Buscar vehiculo por id
    @Operation(summary = "Buscar vehículos por ID", description = "Obtiene un vehículo específico por medio del ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> buscarPorId(
            @Parameter(description = "ID del vehículo a buscar", example = "1")
            @PathVariable Integer id){
        return ResponseEntity.ok(vehiculoService.findById(id));
    }

    // Crear vehiculo
    @Operation(summary = "Crear vehículo", description = "Registra un nuevo vehículo en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehículo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VehiculoDTO> guardar(@Valid @RequestBody VehiculoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.save(dto));
    }

    // Actualizar
    @Operation(summary = "Actualizar vehículo", description = "Actualiza los datos de un vehículo existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)

    })
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoDTO> actualizar(
            @Parameter(description = "ID del vehículo a actualizar", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody VehiculoRequestDTO dto){
        return ResponseEntity.ok(vehiculoService.update(id, dto));
    }

    // Eliminar vehiculo
    @Operation(summary = "Eliminar vehículo", description = "Elimina un vehículo existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehículo eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del vehículo a eliminar", example = "1")
            @PathVariable Integer id){

        boolean eliminado = vehiculoService.delete(id);

        if (!eliminado){

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    // Query method buscar vehiculos disponibles con precio menor al indicado
    @Operation(summary = "Buscar vehículos disponibles por precio menor", description = "Obtiene los vehículos disponibles " +
    "cuyo precio diario sea menor al valor indicado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículos disponibles encontrados"),
            @ApiResponse(responseCode = "204", description = "No existen vehículos disponibles con precio menor al indicado",
            content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping("/disponibles/precio-menor/{precio}")
    public ResponseEntity<List<VehiculoDTO>> buscarDisponiblesPorPrecioMenor(
            @Parameter(description = "Precio máximo diario del vehículo", example = "35000")
            @PathVariable Double precio){

        List<VehiculoDTO> vehiculos = vehiculoService.buscarDisponiblesPorPrecioMenor(precio);

        if (vehiculos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(vehiculos);
    }
}

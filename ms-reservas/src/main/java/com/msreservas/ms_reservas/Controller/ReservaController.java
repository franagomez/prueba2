package com.msreservas.ms_reservas.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.msreservas.ms_reservas.DTO.ReservaDTO;
import com.msreservas.ms_reservas.DTO.ReservaRequestDTO;
import com.msreservas.ms_reservas.Service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
@Tag(name = "Reservas", description = "Operaciones CRUD y consultas de reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;
    //Listar reservas
    @Operation(summary = "Listar reservas", description = "Obtiene todas las reservas registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas"),
            @ApiResponse(responseCode = "204", description = "No existen reservas registradas", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listar(){
        List<ReservaDTO> reservas = reservaService.findAll();

        if(reservas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }
    //Buscar reserva por ID
    @Operation(summary = "Buscar reserva por ID", description = "Obtiene una reserva específica según su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> buscarPorId(
            @Parameter(description = "ID de la reserva a buscar", example = "1")
            @PathVariable Integer id){
        return ResponseEntity.ok(reservaService.findById(id));
    }
    //Crear reserva
    @Operation(summary = "Crear reserva", description = "Registra una nueva reserva en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReservaDTO> guardar(@Valid @RequestBody ReservaRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(dto));
    }

    //Actualizar reserva por ID
    @Operation(summary = "Actualizar reserva", description = "Actualiza los datos de una reserva existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> actualizar(
            @Parameter(description = "ID de la reserva a actualizar", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody ReservaRequestDTO dto){
        return ResponseEntity.ok(reservaService.update(id, dto));
    }

    //Eliminar reservas por ID
    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la reserva a eliminar", example = "1")
            @PathVariable Integer id){
        boolean eliminado = reservaService.delete(id);

        if(!eliminado){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    //QUERY
    @Operation(summary = "Buscar reservas activas por duración mayor",
            description = "Obtiene reservas activas cuya duración en días sea mayor al valor indicado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas activas encontradas"),
            @ApiResponse(responseCode = "204", description = "No existen reservas activas con duración mayor al valor indicado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/activas/dias-mayor/{dias}")
    public ResponseEntity<List<ReservaDTO>> buscarActivasPorDiasMayor(
            @Parameter(description = "Cantidad mínima de días de duración", example = "3")
            @PathVariable Integer dias){

        List<ReservaDTO> reservas = reservaService.buscarActivasPorDiasMayor(dias);

        if(reservas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    //QUERY
    @Operation(summary = "Buscar reservas desde una fecha",
            description = "Obtiene reservas cuya fecha de inicio sea igual o posterior a la fecha indicada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas desde la fecha indicada"),
            @ApiResponse(responseCode = "204", description = "No existen reservas desde la fecha indicada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/desde-fecha/{fecha}")
    public ResponseEntity<List<ReservaDTO>> buscarDesdeFecha(
            @Parameter(description = "Fecha inicial de búsqueda en formato yyyy-mm-dd", example = "2026-03-15")
            @PathVariable LocalDate fecha){

        List<ReservaDTO> reservas = reservaService.buscarDesdeFecha(fecha);

        if(reservas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }
}

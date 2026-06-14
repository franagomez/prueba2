package com.msreservas.ms_reservas.Controller;


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
public class EstadoReservaController {

    @Autowired
    private EstadoReservaService estadoReservaService;

    // Listar
    @GetMapping
    public ResponseEntity<List<EstadoReservaDTO>> listar(){
        List<EstadoReservaDTO> estados = estadoReservaService.findAll();

        if(estados.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(estados);
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<EstadoReservaDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(estadoReservaService.findById(id));
    }

    // Guardar
    @PostMapping
    public ResponseEntity<EstadoReservaDTO> guardar(@Valid @RequestBody EstadoReservaRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoReservaService.save(dto));
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<EstadoReservaDTO> actualizar(@PathVariable Integer id,
                                                       @Valid @RequestBody EstadoReservaRequestDTO dto){
        return ResponseEntity.ok(estadoReservaService.update(id, dto));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        boolean eliminado = estadoReservaService.delete(id);

        if(!eliminado){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }




















}

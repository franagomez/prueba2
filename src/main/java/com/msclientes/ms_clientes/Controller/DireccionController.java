package com.msclientes.ms_clientes.Controller;


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
public class DireccionController {

    @Autowired
    private DireccionService direccionService;


    // Listar todas las direcciones con GET
    @GetMapping
    public ResponseEntity<List<DireccionDTO>> listar(){

        List<DireccionDTO> direcciones = direccionService.findAll();

        if (direcciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(direcciones);
    }

    // Buscar direccion por id
    @GetMapping("/{id}")
    public ResponseEntity<DireccionDTO> buscarPorId(@PathVariable Integer id){

        return ResponseEntity.ok(direccionService.findById(id));
    }

    // Crear direccion
    @PostMapping
    public ResponseEntity<DireccionDTO> guardar(@Valid @RequestBody DireccionRequestDTO dto){

        return ResponseEntity.status(HttpStatus.CREATED).body(direccionService.save(dto));
    }

    // Actualizar direccion

    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody DireccionRequestDTO dto){

        return ResponseEntity.ok(direccionService.update(id, dto));
    }

    // Eliminar direccion

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){

        boolean eliminado = direccionService.delete(id);

        if (!eliminado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

package com.arriendo.ms_empleados.controller;

import com.arriendo.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendo.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendo.ms_empleados.model.Empleado;
import com.arriendo.ms_empleados.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<Empleado>> listar() {
        return ResponseEntity.ok(empleadoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(empleadoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> guardar(@Valid @RequestBody EmpleadoRequestDTO dto) {

        EmpleadoResponseDTO response = empleadoService.guardar(dto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoRequestDTO dto) {

        return ResponseEntity.ok(empleadoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        empleadoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
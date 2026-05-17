package com.msvehiculos.ms_vehiculos.Controller;


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
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    // Listar vehiculos
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> listar(){

        List<VehiculoDTO> vehiculos = vehiculoService.findAll();

        if (vehiculos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(vehiculos);
    }

    // Buscar vehiculo por id
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(vehiculoService.findById(id));
    }

    // Crear vehiculo
    @PostMapping
    public ResponseEntity<VehiculoDTO> guardar(@Valid @RequestBody VehiculoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.save(dto));
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody VehiculoRequestDTO dto){
        return ResponseEntity.ok(vehiculoService.update(id, dto));
    }

    // Eliminar vehiculo
    @DeleteMapping
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){

        boolean eliminado = vehiculoService.delete(id);

        if (!eliminado){

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    // Query method buscar vehiculos disponibles con precio menor al indicado
    @GetMapping("/disponibles/precio-menor/{precio}")
    public ResponseEntity<List<VehiculoDTO>> buscarDisponiblesPorPrecioMenor(@PathVariable Double precio){

        List<VehiculoDTO> vehiculos = vehiculoService.buscarDisponiblesPorPrecioMenor(precio);

        if (vehiculos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(vehiculos);
    }
}

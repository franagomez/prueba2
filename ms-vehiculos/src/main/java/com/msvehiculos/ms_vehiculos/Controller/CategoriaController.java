package com.msvehiculos.ms_vehiculos.Controller;


import com.msvehiculos.ms_vehiculos.DTO.CategoriaDTO;
import com.msvehiculos.ms_vehiculos.DTO.CategoriaRequestDTO;
import com.msvehiculos.ms_vehiculos.Service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // Listar categorias
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar(){

        List<CategoriaDTO> categorias = categoriaService.findAll();

        if (categorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categorias);
    }

    // Buscar categoria por id
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    // Crear categoria
    @PostMapping
    public ResponseEntity<CategoriaDTO> guardar(@Valid @RequestBody CategoriaRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(dto));
    }

    // Actualizar categoria
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody CategoriaRequestDTO dto){
        return ResponseEntity.ok(categoriaService.update(id, dto));
    }

    // Eliminar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){

        boolean eliminado = categoriaService.delete(id);

        if(!eliminado){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
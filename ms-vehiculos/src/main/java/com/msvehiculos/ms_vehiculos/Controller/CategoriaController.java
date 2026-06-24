package com.msvehiculos.ms_vehiculos.Controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Categorías", description = "Operaciones CRUD de categorías de vehículos")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // Listar categorias
    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías encontradas"),
            @ApiResponse(responseCode = "204", description = "No existen categorías registradas", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar(){

        List<CategoriaDTO> categorias = categoriaService.findAll();

        if (categorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    // Buscar categoria por id
    @Operation(summary = "Buscar categoría por ID", description = "Obtiene una categoría específica por medio del ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(
            @Parameter(description = "ID de la categoría a buscar", example = "1")
            @PathVariable Integer id){
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    // Crear categoria
    @Operation(summary = "Crear categoría", description = "Registra una nueva categoría de vehículos en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CategoriaDTO> guardar(@Valid @RequestBody CategoriaRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(dto));
    }

    // Actualizar categoria
    @Operation(summary = "Actualizar categoría", description = "Actualiza los datos de una categoría existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(
            @Parameter(description = "ID de la categoría a actualizar", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaRequestDTO dto){
        return ResponseEntity.ok(categoriaService.update(id, dto));
    }

    // Eliminar categoria
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la categoría a eliminar", example = "1")
            @PathVariable Integer id){

        boolean eliminado = categoriaService.delete(id);

        if(!eliminado){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
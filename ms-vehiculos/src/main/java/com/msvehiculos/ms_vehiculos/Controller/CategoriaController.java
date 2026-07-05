package com.msvehiculos.ms_vehiculos.Controller;

import com.msvehiculos.ms_vehiculos.Assembler.CategoriaModelAssembler;
import com.msvehiculos.ms_vehiculos.DTO.CategoriaDTO;
import com.msvehiculos.ms_vehiculos.DTO.CategoriaRequestDTO;
import com.msvehiculos.ms_vehiculos.Service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorías", description = "Operaciones CRUD de categorías de vehículos")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaModelAssembler categoriaModelAssembler;

    public CategoriaController(CategoriaService categoriaService, CategoriaModelAssembler categoriaModelAssembler) {
        this.categoriaService = categoriaService;
        this.categoriaModelAssembler = categoriaModelAssembler;
    }

    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías encontradas"),
            @ApiResponse(responseCode = "204", description = "No existen categorías registradas", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CategoriaDTO>>> listar() {
        log.info("GET /api/v1/categorias");
        List<CategoriaDTO> categorias = categoriaService.findAll();

        if (categorias.isEmpty()) {
            log.warn("No se encontraron categorías registradas");
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<CategoriaDTO>> categoriasConLinks = categorias.stream()
                .map(categoriaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(categoriasConLinks,
                linkTo(methodOn(CategoriaController.class).listar()).withSelfRel()));
    }

    @Operation(summary = "Buscar categoría por ID", description = "Obtiene una categoría específica por medio del ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CategoriaDTO>> buscarPorId(
            @Parameter(description = "ID de la categoría a buscar", example = "1")
            @PathVariable Integer id) {
        log.info("GET /api/v1/categorias/{}", id);
        CategoriaDTO categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoriaModelAssembler.toModel(categoria));
    }

    @Operation(summary = "Crear categoría", description = "Registra una nueva categoría de vehículos en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<CategoriaDTO>> guardar(@Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("POST /api/v1/categorias con nombre {}", dto.getNombre());
        CategoriaDTO categoria = categoriaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaModelAssembler.toModel(categoria));
    }

    @Operation(summary = "Actualizar categoría", description = "Actualiza los datos de una categoría existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CategoriaDTO>> actualizar(
            @Parameter(description = "ID de la categoría a actualizar", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("PUT /api/v1/categorias/{}", id);
        CategoriaDTO categoria = categoriaService.update(id, dto);
        return ResponseEntity.ok(categoriaModelAssembler.toModel(categoria));
    }

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la categoría a eliminar", example = "1")
            @PathVariable Integer id) {
        log.info("DELETE /api/v1/categorias/{}", id);
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

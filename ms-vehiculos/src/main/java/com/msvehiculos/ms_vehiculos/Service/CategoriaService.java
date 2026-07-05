package com.msvehiculos.ms_vehiculos.Service;

import com.msvehiculos.ms_vehiculos.DTO.CategoriaDTO;
import com.msvehiculos.ms_vehiculos.DTO.CategoriaRequestDTO;
import com.msvehiculos.ms_vehiculos.Exception.ResourceNotFoundException;
import com.msvehiculos.ms_vehiculos.Mapper.CategoriaMapper;
import com.msvehiculos.ms_vehiculos.Model.Categoria;
import com.msvehiculos.ms_vehiculos.Repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAll() {
        log.info("Listando categorías");
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDTO> listaDTO = new ArrayList<>();
        for (Categoria categoria : categorias) {
            listaDTO.add(categoriaMapper.toDTO(categoria));
        }
        log.info("Se encontraron {} categorías", listaDTO.size());
        return listaDTO;
    }

    @Transactional(readOnly = true)
    public CategoriaDTO findById(Integer id) {
        log.info("Buscando categoría con id {}", id);
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada con id {}", id);
                    return new ResourceNotFoundException("Categoria no encontrada");
                });
        return categoriaMapper.toDTO(categoria);
    }

    public CategoriaDTO save(CategoriaRequestDTO dto) {
        log.info("Creando categoría {}", dto.getNombre());
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria guardada = categoriaRepository.save(categoria);
        log.info("Categoría guardada correctamente con id {}", guardada.getId());
        return categoriaMapper.toDTO(guardada);
    }

    public CategoriaDTO update(Integer id, CategoriaRequestDTO dto) {
        log.info("Actualizando categoría con id {}", id);
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada con id {} al intentar actualizar", id);
                    return new ResourceNotFoundException("Categoria no encontrada");
                });

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setCantidadVehiculos(dto.getCantidadVehiculos());
        categoria.setActiva(dto.isActiva());
        categoria.setFechaCreacion(dto.getFechaCreacion());

        Categoria actualizada = categoriaRepository.save(categoria);
        log.info("Categoría con id {} actualizada correctamente", actualizada.getId());
        return categoriaMapper.toDTO(actualizada);
    }

    public void delete(Integer id) {
        log.info("Eliminando categoría con id {}", id);
        if (!categoriaRepository.existsById(id)) {
            log.error("Categoría no encontrada con id {} al intentar eliminar", id);
            throw new ResourceNotFoundException("Categoria no encontrada");
        }
        categoriaRepository.deleteById(id);
        log.info("Categoría con id {} eliminada correctamente", id);
    }
}

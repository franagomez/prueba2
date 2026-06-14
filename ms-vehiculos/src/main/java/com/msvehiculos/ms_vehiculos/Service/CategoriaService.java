package com.msvehiculos.ms_vehiculos.Service;


import com.msvehiculos.ms_vehiculos.DTO.CategoriaDTO;
import com.msvehiculos.ms_vehiculos.DTO.CategoriaRequestDTO;
import com.msvehiculos.ms_vehiculos.Exception.ResourceNotFoundException;
import com.msvehiculos.ms_vehiculos.Mapper.CategoriaMapper;
import com.msvehiculos.ms_vehiculos.Model.Categoria;
import com.msvehiculos.ms_vehiculos.Repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    // Listar Categorias
    public List<CategoriaDTO> findAll(){
        List<Categoria> categorias = categoriaRepository.findAll();


        List<CategoriaDTO> listaDTO = new ArrayList<>();

        for (Categoria categoria : categorias) {
            listaDTO.add(categoriaMapper.toDTO(categoria));
        }

        return listaDTO;

    }

    // Buscar categoria por id
    public CategoriaDTO findById(Integer id){

        Categoria categoria = categoriaRepository.findById(id).orElse(null);

        if (categoria == null){
            throw new ResourceNotFoundException("Categoria no encontrada");
        }

        return categoriaMapper.toDTO(categoria);

    }


    // Crear categoria
    public CategoriaDTO save(CategoriaRequestDTO dto){

        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria guardada = categoriaRepository.save(categoria);

        return categoriaMapper.toDTO(guardada);
    }

    // Actualizar categoria
    public CategoriaDTO update(Integer id, CategoriaRequestDTO dto){

        Categoria categoria = categoriaRepository.findById(id).orElse(null);

        if (categoria == null){
            throw new ResourceNotFoundException("Categoria no encontrada");
        }

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setCantidadVehiculos(dto.getCantidadVehiculos());
        categoria.setActiva(dto.isActiva());
        categoria.setFechaCreacion(dto.getFechaRegistro());

        Categoria actualizada = categoriaRepository.save(categoria);

        return categoriaMapper.toDTO(actualizada);
    }

    // Eliminar categoria
    public boolean delete(Integer id){

        if(categoriaRepository.existsById(id)){
            categoriaRepository.deleteById(id);
            return true;
        }

        return false;
    }

}

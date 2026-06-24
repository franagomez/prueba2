package com.msvehiculos.ms_vehiculos.Mapper;


import com.msvehiculos.ms_vehiculos.DTO.CategoriaDTO;
import com.msvehiculos.ms_vehiculos.DTO.CategoriaRequestDTO;
import com.msvehiculos.ms_vehiculos.Model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();

        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setCantidadVehiculos(categoria.getCantidadVehiculos());
        dto.setActiva(categoria.isActiva());
        dto.setFechaCreacion(categoria.getFechaCreacion());

        return dto;
    }

    public Categoria toEntity(CategoriaRequestDTO dto) {

        Categoria categoria = new Categoria();

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setCantidadVehiculos(dto.getCantidadVehiculos());
        categoria.setActiva(dto.isActiva());
        categoria.setFechaCreacion(dto.getFechaCreacion());

        return categoria;

    }
}

package com.msvehiculos.ms_vehiculos.Mapper;


import com.msvehiculos.ms_vehiculos.DTO.VehiculoDTO;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoRequestDTO;
import com.msvehiculos.ms_vehiculos.Model.Categoria;
import com.msvehiculos.ms_vehiculos.Model.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    // Pasar de entity a DTO

    public VehiculoDTO toDTO(Vehiculo vehiculo){

        VehiculoDTO dto = new VehiculoDTO();

        dto.setId((vehiculo.getId()));
        dto.setPatente(vehiculo.getPatente());
        dto.setMarca(vehiculo.getMarca());
        dto.setModelo(vehiculo.getModelo());
        dto.setAnio(vehiculo.getAnio());
        dto.setPrecioArriendoDiario(vehiculo.getPrecioArriendoDiario());
        dto.setDisponible(vehiculo.isDisponible());
        dto.setFechaRegistro(vehiculo.getFechaRegistro());

        if (vehiculo.getCategoria() != null){
            dto.setCategoriaId(vehiculo.getCategoria().getId());
        }

        return dto;
    }

    // pasamos ell request dto a entity

    public Vehiculo toEntity(VehiculoRequestDTO dto){

        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setPatente(dto.getPatente());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setAnio(dto.getAnio());
        vehiculo.setPrecioArriendoDiario(dto.getPrecioArriendoDiario());
        vehiculo.setDisponible(dto.isDisponible());
        vehiculo.setFechaRegistro(dto.getFechaRegistro());

        Categoria categoria = new Categoria();
        categoria.setId(dto.getCategoriaId());

        vehiculo.setCategoria(categoria);

        return vehiculo;
    }

}


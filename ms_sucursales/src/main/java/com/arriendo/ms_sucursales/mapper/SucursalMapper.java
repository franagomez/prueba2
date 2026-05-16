package com.arriendo.ms_sucursales.mapper;

import com.arriendo.ms_sucursales.dto.SucursalRequestDTO;
import com.arriendo.ms_sucursales.dto.SucursalResponseDTO;
import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.model.Sucursal;

public class SucursalMapper {

    public static Sucursal toEntity(SucursalRequestDTO dto, Region region) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setCapacidadVehiculos(dto.getCapacidadVehiculos());
        sucursal.setOperativa(dto.getOperativa());
        sucursal.setFechaApertura(dto.getFechaApertura());
        sucursal.setRegion(region);
        return sucursal;
    }

    public static SucursalResponseDTO toDTO(Sucursal sucursal) {
        return new SucursalResponseDTO(
                sucursal.getId(),
                sucursal.getNombre(),
                sucursal.getDireccion(),
                sucursal.getCapacidadVehiculos(),
                sucursal.getOperativa(),
                sucursal.getFechaApertura(),
                sucursal.getRegion().getId(),
                sucursal.getRegion().getNombre()
        );
    }
}
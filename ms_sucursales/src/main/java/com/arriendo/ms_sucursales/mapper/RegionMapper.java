package com.arriendo.ms_sucursales.mapper;

import com.arriendo.ms_sucursales.dto.RegionRequestDTO;
import com.arriendo.ms_sucursales.dto.RegionResponseDTO;
import com.arriendo.ms_sucursales.model.Region;

public class RegionMapper {

    public static Region toEntity(RegionRequestDTO dto) {
        Region region = new Region();
        region.setNombre(dto.getNombre());
        region.setCodigo(dto.getCodigo());
        region.setNumeroRegion(dto.getNumeroRegion());
        region.setActivo(dto.getActivo());
        region.setFechaCreacion(dto.getFechaCreacion());
        return region;
    }

    public static RegionResponseDTO toDTO(Region region) {
        return new RegionResponseDTO(
                region.getId(),
                region.getNombre(),
                region.getCodigo(),
                region.getNumeroRegion(),
                region.getActivo(),
                region.getFechaCreacion()
        );
    }
}

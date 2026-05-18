package com.arriendo.ms_sucursales.service;

import com.arriendo.ms_sucursales.dto.RegionRequestDTO;
import com.arriendo.ms_sucursales.dto.RegionResponseDTO;
import com.arriendo.ms_sucursales.exception.ResourceNotFoundException;
import com.arriendo.ms_sucursales.mapper.RegionMapper;
import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> obtenerTodas() {
        return regionRepository.findAll();
    }

    public Region obtenerPorId(Integer id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Región no encontrada"));
    }

    public RegionResponseDTO guardar(RegionRequestDTO dto) {
        Region region = RegionMapper.toEntity(dto);
        Region regionGuardada = regionRepository.save(region);
        return RegionMapper.toDTO(regionGuardada);
    }

    public Region actualizar(Integer id, RegionRequestDTO dto) {
        Region region = obtenerPorId(id);

        region.setNombre(dto.getNombre());
        region.setCodigo(dto.getCodigo());
        region.setNumeroRegion(dto.getNumeroRegion());
        region.setActivo(dto.getActivo());
        region.setFechaCreacion(dto.getFechaCreacion());

        return regionRepository.save(region);
    }

    public void eliminar(Integer id) {
        Region region = obtenerPorId(id);
        regionRepository.delete(region);
    }
}
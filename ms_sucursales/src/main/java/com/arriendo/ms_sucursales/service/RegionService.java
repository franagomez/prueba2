package com.arriendo.ms_sucursales.service;

import com.arriendo.ms_sucursales.dto.RegionRequestDTO;
import com.arriendo.ms_sucursales.dto.RegionResponseDTO;
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

    public RegionResponseDTO guardar(RegionRequestDTO dto) {

        Region region = RegionMapper.toEntity(dto);

        Region regionGuardada = regionRepository.save(region);

        return RegionMapper.toDTO(regionGuardada);
    }
}
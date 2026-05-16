package com.arriendo.ms_sucursales.service;

import com.arriendo.ms_sucursales.dto.SucursalRequestDTO;
import com.arriendo.ms_sucursales.dto.SucursalResponseDTO;
import com.arriendo.ms_sucursales.mapper.SucursalMapper;
import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.model.Sucursal;
import com.arriendo.ms_sucursales.repository.RegionRepository;
import com.arriendo.ms_sucursales.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private RegionRepository regionRepository;

    public List<Sucursal> obtenerTodas() {
        return sucursalRepository.findAll();
    }

    public SucursalResponseDTO guardar(SucursalRequestDTO dto) {

        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new RuntimeException("Región no encontrada"));

        Sucursal sucursal = SucursalMapper.toEntity(dto, region);

        Sucursal sucursalGuardada = sucursalRepository.save(sucursal);

        return SucursalMapper.toDTO(sucursalGuardada);
    }
}

package com.arriendo.ms_sucursales.service;

import com.arriendo.ms_sucursales.dto.SucursalRequestDTO;
import com.arriendo.ms_sucursales.dto.SucursalResponseDTO;
import com.arriendo.ms_sucursales.exception.ResourceNotFoundException;
import com.arriendo.ms_sucursales.mapper.SucursalMapper;
import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.model.Sucursal;
import com.arriendo.ms_sucursales.repository.RegionRepository;
import com.arriendo.ms_sucursales.repository.SucursalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {

    private static final Logger log =
            LoggerFactory.getLogger(SucursalService.class);

    private final SucursalRepository sucursalRepository;
    private final RegionRepository regionRepository;

    public SucursalService(SucursalRepository sucursalRepository, RegionRepository regionRepository) {
        this.sucursalRepository = sucursalRepository;
        this.regionRepository = regionRepository;
    }

    public List<Sucursal> obtenerTodas() {
        log.info("Listando todas las sucursales");
        return sucursalRepository.findAll();
    }

    public List<Sucursal> buscarOperativas() {
        log.info("Buscando sucursales operativas");
        return sucursalRepository.buscarSucursalesOperativas();
    }

    public Sucursal obtenerPorId(Integer id) {
        log.info("Buscando sucursal con id {}", id);

        return sucursalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sucursal no encontrada"));
    }

    public SucursalResponseDTO guardar(SucursalRequestDTO dto) {
        log.info("Guardando nueva sucursal");

        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Región no encontrada"));

        Sucursal sucursal = SucursalMapper.toEntity(dto, region);
        Sucursal sucursalGuardada = sucursalRepository.save(sucursal);

        return SucursalMapper.toDTO(sucursalGuardada);
    }

    public Sucursal actualizar(Integer id, SucursalRequestDTO dto) {
        log.info("Actualizando sucursal con id {}", id);

        Sucursal sucursal = obtenerPorId(id);

        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Región no encontrada"));

        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setCapacidadVehiculos(dto.getCapacidadVehiculos());
        sucursal.setOperativa(dto.getOperativa());
        sucursal.setFechaApertura(dto.getFechaApertura());
        sucursal.setRegion(region);

        return sucursalRepository.save(sucursal);
    }

    public void eliminar(Integer id) {
        log.info("Eliminando sucursal con id {}", id);

        Sucursal sucursal = obtenerPorId(id);
        sucursalRepository.delete(sucursal);
    }
}
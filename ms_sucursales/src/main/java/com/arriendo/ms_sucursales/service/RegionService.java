package com.arriendo.ms_sucursales.service;

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

    public Region guardar(Region region) {
        return regionRepository.save(region);
    }
}

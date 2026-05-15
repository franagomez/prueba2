package com.arriendo.ms_sucursales.controller;

import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public List<Region> listar() {
        return regionService.obtenerTodas();
    }

    @PostMapping
    public Region guardar(@RequestBody Region region) {
        return regionService.guardar(region);
    }
}

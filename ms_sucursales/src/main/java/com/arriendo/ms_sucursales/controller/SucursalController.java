package com.arriendo.ms_sucursales.controller;

import com.arriendo.ms_sucursales.model.Sucursal;
import com.arriendo.ms_sucursales.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public List<Sucursal> listar() {
        return sucursalService.obtenerTodas();
    }

    @PostMapping
    public Sucursal guardar(@RequestBody Sucursal sucursal) {
        return sucursalService.guardar(sucursal);
    }
}

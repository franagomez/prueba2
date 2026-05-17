package com.msvehiculos.ms_vehiculos.Service;


import com.msvehiculos.ms_vehiculos.Mapper.VehiculoMapper;
import com.msvehiculos.ms_vehiculos.Repository.CategoriaRepository;
import com.msvehiculos.ms_vehiculos.Repository.VehiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private VehiculoMapper vehiculoMapper;

    @Autowired
    private CategoriaRepository categoriaRepository;


}

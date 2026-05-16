package com.arriendo.ms_pagos.service;

import com.arriendo.ms_pagos.dto.PagoRequestDTO;
import com.arriendo.ms_pagos.dto.PagoResponseDTO;
import com.arriendo.ms_pagos.mapper.PagoMapper;
import com.arriendo.ms_pagos.model.Pago;
import com.arriendo.ms_pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public PagoResponseDTO guardar(PagoRequestDTO dto) {

        Pago pago = PagoMapper.toEntity(dto);

        Pago pagoGuardado = pagoRepository.save(pago);

        return PagoMapper.toDTO(pagoGuardado);
    }
}

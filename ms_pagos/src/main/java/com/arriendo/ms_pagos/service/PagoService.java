package com.arriendo.ms_pagos.service;

import com.arriendo.ms_pagos.dto.PagoRequestDTO;
import com.arriendo.ms_pagos.dto.PagoResponseDTO;
import com.arriendo.ms_pagos.exception.ResourceNotFoundException;
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

    public List<Pago> buscarPorRango(Double minimo, Double maximo) {

        return pagoRepository.buscarPagosPorRango(minimo, maximo);
    }

    public Pago obtenerPorId(Long id) {

        return pagoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pago no encontrado"));
    }

    public PagoResponseDTO guardar(PagoRequestDTO dto) {

        Pago pago = PagoMapper.toEntity(dto);

        Pago pagoGuardado = pagoRepository.save(pago);

        return PagoMapper.toDTO(pagoGuardado);
    }

    public Pago actualizar(Long id, PagoRequestDTO dto) {

        Pago pago = obtenerPorId(id);

        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setPagado(dto.getPagado());
        pago.setFechaPago(dto.getFechaPago());
        pago.setDescripcion(dto.getDescripcion());

        return pagoRepository.save(pago);
    }

    public void eliminar(Long id) {

        Pago pago = obtenerPorId(id);

        pagoRepository.delete(pago);
    }
}

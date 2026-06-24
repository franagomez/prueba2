package com.arriendo.ms_pagos.mapper;

import com.arriendo.ms_pagos.dto.PagoRequestDTO;
import com.arriendo.ms_pagos.dto.PagoResponseDTO;
import com.arriendo.ms_pagos.model.Pago;

public class PagoMapper {

    public static Pago toEntity(PagoRequestDTO dto) {

        Pago pago = new Pago();

        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setPagado(dto.getPagado());
        pago.setFechaPago(dto.getFechaPago());
        pago.setDescripcion(dto.getDescripcion());

        return pago;
    }

    public static PagoResponseDTO toDTO(Pago pago) {

        return new PagoResponseDTO(
                pago.getId(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getPagado(),
                pago.getFechaPago(),
                pago.getDescripcion()
        );
    }
}

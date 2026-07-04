package com.arriendo.ms_pagos.assembler;

import com.arriendo.ms_pagos.controller.PagoController;
import com.arriendo.ms_pagos.model.Pago;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoController.class).buscarPorId(pago.getId())).withSelfRel(),
                linkTo(methodOn(PagoController.class).listar()).withRel("todos-los-pagos"),
                linkTo(methodOn(PagoController.class).buscarPorRango(10000.0, 100000.0)).withRel("pagos-por-rango")
        );
    }
}

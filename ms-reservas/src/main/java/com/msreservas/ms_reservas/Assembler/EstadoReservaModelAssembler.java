package com.msreservas.ms_reservas.Assembler;

import com.msreservas.ms_reservas.Controller.EstadoReservaController;
import com.msreservas.ms_reservas.DTO.EstadoReservaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstadoReservaModelAssembler implements RepresentationModelAssembler<EstadoReservaDTO, EntityModel<EstadoReservaDTO>> {

    @Override
    public EntityModel<EstadoReservaDTO> toModel(EstadoReservaDTO estado) {
        return EntityModel.of(estado,
                linkTo(methodOn(EstadoReservaController.class).buscarPorId(estado.getId())).withSelfRel(),
                linkTo(methodOn(EstadoReservaController.class).listar()).withRel("estados-reserva"));
    }
}

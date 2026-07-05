package com.msreservas.ms_reservas.Assembler;

import com.msreservas.ms_reservas.Controller.ReservaController;
import com.msreservas.ms_reservas.DTO.ReservaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReservaModelAssembler implements RepresentationModelAssembler<ReservaDTO, EntityModel<ReservaDTO>> {

    @Override
    public EntityModel<ReservaDTO> toModel(ReservaDTO reserva) {
        return EntityModel.of(reserva,
                linkTo(methodOn(ReservaController.class).buscarPorId(reserva.getId())).withSelfRel(),
                linkTo(methodOn(ReservaController.class).listar()).withRel("todas-las-reservas"),
                linkTo(methodOn(ReservaController.class).buscarActivasPorDiasMayor(3)).withRel("reservas-activas-dias-mayor"),
                linkTo(methodOn(ReservaController.class).buscarDesdeFecha(LocalDate.now())).withRel("reservas-desde-fecha"));
    }
}

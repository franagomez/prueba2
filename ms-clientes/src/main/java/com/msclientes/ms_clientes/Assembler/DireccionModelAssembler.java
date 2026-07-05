package com.msclientes.ms_clientes.Assembler;

import com.msclientes.ms_clientes.Controller.DireccionController;
import com.msclientes.ms_clientes.DTO.DireccionDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DireccionModelAssembler implements RepresentationModelAssembler<DireccionDTO, EntityModel<DireccionDTO>> {

    @Override
    public EntityModel<DireccionDTO> toModel(DireccionDTO direccion) {
        return EntityModel.of(direccion,
                linkTo(methodOn(DireccionController.class).buscarPorId(direccion.getId())).withSelfRel(),
                linkTo(methodOn(DireccionController.class).listar()).withRel("direcciones"));
    }
}

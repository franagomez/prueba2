package com.msclientes.ms_clientes.Assembler;

import com.msclientes.ms_clientes.Controller.ClienteController;
import com.msclientes.ms_clientes.DTO.ClienteDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteDTO, EntityModel<ClienteDTO>> {

    @Override
    public EntityModel<ClienteDTO> toModel(ClienteDTO cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).buscarPorId(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).listar()).withRel("clientes"));
    }
}

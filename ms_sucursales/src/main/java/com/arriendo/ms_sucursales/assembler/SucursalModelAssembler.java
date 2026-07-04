package com.arriendo.ms_sucursales.assembler;

import com.arriendo.ms_sucursales.controller.SucursalController;
import com.arriendo.ms_sucursales.model.Sucursal;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SucursalModelAssembler
        implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(
                sucursal,
                linkTo(methodOn(SucursalController.class).buscarPorId(sucursal.getId())).withSelfRel(),
                linkTo(methodOn(SucursalController.class).listar()).withRel("todas-las-sucursales"),
                linkTo(methodOn(SucursalController.class).buscarOperativas()).withRel("sucursales-operativas")
        );
    }

    @Override
    public CollectionModel<EntityModel<Sucursal>> toCollectionModel(Iterable<? extends Sucursal> sucursales) {
        List<EntityModel<Sucursal>> modelos = StreamSupport.stream(sucursales.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(modelos);
    }
}

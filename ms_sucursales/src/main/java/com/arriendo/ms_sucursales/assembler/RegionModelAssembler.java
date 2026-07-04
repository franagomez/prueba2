package com.arriendo.ms_sucursales.assembler;

import com.arriendo.ms_sucursales.controller.RegionController;
import com.arriendo.ms_sucursales.model.Region;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RegionModelAssembler
        implements RepresentationModelAssembler<Region, EntityModel<Region>> {

    @Override
    public EntityModel<Region> toModel(Region region) {
        return EntityModel.of(
                region,
                linkTo(methodOn(RegionController.class).buscarPorId(region.getId())).withSelfRel(),
                linkTo(methodOn(RegionController.class).listar()).withRel("todas-las-regiones")
        );
    }

    @Override
    public CollectionModel<EntityModel<Region>> toCollectionModel(Iterable<? extends Region> regiones) {
        List<EntityModel<Region>> modelos = StreamSupport.stream(regiones.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(modelos);
    }
}

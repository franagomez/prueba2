package com.msvehiculos.ms_vehiculos.Assembler;

import com.msvehiculos.ms_vehiculos.Controller.VehiculoController;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehiculoModelAssembler implements RepresentationModelAssembler<VehiculoDTO, EntityModel<VehiculoDTO>> {

    @Override
    public EntityModel<VehiculoDTO> toModel(VehiculoDTO vehiculo) {
        return EntityModel.of(vehiculo,
                linkTo(methodOn(VehiculoController.class).buscarPorId(vehiculo.getId())).withSelfRel(),
                linkTo(methodOn(VehiculoController.class).listar()).withRel("todos-los-vehiculos"),
                linkTo(methodOn(VehiculoController.class).buscarDisponiblesPorPrecioMenor(35000.0)).withRel("vehiculos-disponibles-precio-menor"));
    }
}

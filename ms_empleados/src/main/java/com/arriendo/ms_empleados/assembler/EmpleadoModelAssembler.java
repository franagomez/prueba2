package com.arriendo.ms_empleados.assembler;

import com.arriendo.ms_empleados.controller.EmpleadoController;
import com.arriendo.ms_empleados.model.Empleado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<Empleado, EntityModel<Empleado>> {

    @Override
    public EntityModel<Empleado> toModel(Empleado empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(EmpleadoController.class).buscarPorId(empleado.getId())).withSelfRel(),
                linkTo(methodOn(EmpleadoController.class).listar()).withRel("todos-los-empleados"),
                linkTo(methodOn(EmpleadoController.class).buscarActivosPorAnio(2024)).withRel("empleados-activos-por-anio")
        );
    }
}

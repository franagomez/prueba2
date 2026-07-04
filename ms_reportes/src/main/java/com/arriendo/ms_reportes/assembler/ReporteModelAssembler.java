package com.arriendo.ms_reportes.assembler;

import com.arriendo.ms_reportes.controller.ReporteController;
import com.arriendo.ms_reportes.model.Reporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>> {

    @Override
    public EntityModel<Reporte> toModel(Reporte reporte) {
        return EntityModel.of(reporte,
                linkTo(methodOn(ReporteController.class).buscarPorId(reporte.getId())).withSelfRel(),
                linkTo(methodOn(ReporteController.class).listar()).withRel("todos-los-reportes"),
                linkTo(methodOn(ReporteController.class).obtenerPagos()).withRel("pagos"),
                linkTo(methodOn(ReporteController.class).obtenerReservas()).withRel("reservas")
        );
    }
}

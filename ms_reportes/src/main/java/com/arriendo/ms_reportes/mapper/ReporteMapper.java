package com.arriendo.ms_reportes.mapper;

import com.arriendo.ms_reportes.dto.ReporteRequestDTO;
import com.arriendo.ms_reportes.dto.ReporteResponseDTO;
import com.arriendo.ms_reportes.model.Reporte;

public class ReporteMapper {

    public static Reporte toEntity(ReporteRequestDTO dto) {

        Reporte reporte = new Reporte();

        reporte.setTipoReporte(dto.getTipoReporte());
        reporte.setDescripcion(dto.getDescripcion());
        reporte.setTotalIngresos(dto.getTotalIngresos());
        reporte.setTotalReservas(dto.getTotalReservas());
        reporte.setFechaGeneracion(dto.getFechaGeneracion());

        return reporte;
    }

    public static ReporteResponseDTO toDTO(Reporte reporte) {

        return new ReporteResponseDTO(
                reporte.getId(),
                reporte.getTipoReporte(),
                reporte.getDescripcion(),
                reporte.getTotalIngresos(),
                reporte.getTotalReservas(),
                reporte.getFechaGeneracion()
        );
    }
}

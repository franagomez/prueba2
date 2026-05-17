package com.arriendo.ms_reportes.service;

import com.arriendo.ms_reportes.client.PagoClient;
import com.arriendo.ms_reportes.dto.ReporteRequestDTO;
import com.arriendo.ms_reportes.dto.ReporteResponseDTO;
import com.arriendo.ms_reportes.exception.ResourceNotFoundException;
import com.arriendo.ms_reportes.mapper.ReporteMapper;
import com.arriendo.ms_reportes.model.Reporte;
import com.arriendo.ms_reportes.repository.ReporteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReporteService {

    private static final Logger log =
            LoggerFactory.getLogger(ReporteService.class);

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private PagoClient pagoClient;

    public List<Reporte> obtenerTodos() {

        log.info("Listando todos los reportes");

        return reporteRepository.findAll();
    }

    public List<Map<String, Object>> obtenerPagos() {

        log.info("Obteniendo pagos desde ms-pagos");

        return pagoClient.obtenerPagos();
    }

    public Reporte obtenerPorId(Long id) {

        log.info("Buscando reporte con id {}", id);

        return reporteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reporte no encontrado"));
    }

    public ReporteResponseDTO guardar(ReporteRequestDTO dto) {

        log.info("Guardando nuevo reporte");

        Reporte reporte = ReporteMapper.toEntity(dto);

        Reporte reporteGuardado = reporteRepository.save(reporte);

        return ReporteMapper.toDTO(reporteGuardado);
    }

    public Reporte actualizar(Long id, ReporteRequestDTO dto) {

        log.info("Actualizando reporte con id {}", id);

        Reporte reporte = obtenerPorId(id);

        reporte.setTipoReporte(dto.getTipoReporte());
        reporte.setDescripcion(dto.getDescripcion());
        reporte.setTotalIngresos(dto.getTotalIngresos());
        reporte.setTotalReservas(dto.getTotalReservas());
        reporte.setFechaGeneracion(dto.getFechaGeneracion());

        return reporteRepository.save(reporte);
    }

    public void eliminar(Long id) {

        log.info("Eliminando reporte con id {}", id);

        Reporte reporte = obtenerPorId(id);

        reporteRepository.delete(reporte);
    }
}

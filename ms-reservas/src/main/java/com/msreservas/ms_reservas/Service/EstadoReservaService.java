package com.msreservas.ms_reservas.Service;

import com.msreservas.ms_reservas.DTO.EstadoReservaDTO;
import com.msreservas.ms_reservas.DTO.EstadoReservaRequestDTO;
import com.msreservas.ms_reservas.Exception.ResourceNotFoundException;
import com.msreservas.ms_reservas.Mapper.EstadoReservaMapper;
import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Repository.EstadoReservaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class EstadoReservaService {

    private final EstadoReservaRepository estadoReservaRepository;
    private final EstadoReservaMapper estadoReservaMapper;

    public EstadoReservaService(EstadoReservaRepository estadoReservaRepository,
                                 EstadoReservaMapper estadoReservaMapper) {
        this.estadoReservaRepository = estadoReservaRepository;
        this.estadoReservaMapper = estadoReservaMapper;
    }

    @Transactional(readOnly = true)
    public List<EstadoReservaDTO> findAll() {
        log.info("Listando estados de reserva");
        List<EstadoReserva> estados = estadoReservaRepository.findAll();
        List<EstadoReservaDTO> listaDTO = new ArrayList<>();
        for (EstadoReserva estado : estados) {
            listaDTO.add(estadoReservaMapper.toDTO(estado));
        }
        log.info("Se encontraron {} estados de reserva", listaDTO.size());
        return listaDTO;
    }

    @Transactional(readOnly = true)
    public EstadoReservaDTO findById(Integer id) {
        log.info("Buscando estado de reserva con id {}", id);
        EstadoReserva estado = estadoReservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Estado de reserva no encontrado con id {}", id);
                    return new ResourceNotFoundException("Estado de reserva no encontrado");
                });
        return estadoReservaMapper.toDTO(estado);
    }

    public EstadoReservaDTO save(EstadoReservaRequestDTO dto) {
        log.info("Creando estado de reserva {}", dto.getNombre());
        EstadoReserva estado = estadoReservaMapper.toEntity(dto);
        EstadoReserva guardado = estadoReservaRepository.save(estado);
        log.info("Estado de reserva guardado correctamente con id {}", guardado.getId());
        return estadoReservaMapper.toDTO(guardado);
    }

    public EstadoReservaDTO update(Integer id, EstadoReservaRequestDTO dto) {
        log.info("Actualizando estado de reserva con id {}", id);
        EstadoReserva estado = estadoReservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Estado de reserva no encontrado con id {} al intentar actualizar", id);
                    return new ResourceNotFoundException("Estado de reserva no encontrado");
                });

        estado.setNombre(dto.getNombre());
        estado.setDescripcion(dto.getDescripcion());
        estado.setPrioridad(dto.getPrioridad());
        estado.setActivo(dto.isActivo());
        estado.setFechaCreacion(dto.getFechaCreacion());

        EstadoReserva actualizado = estadoReservaRepository.save(estado);
        log.info("Estado de reserva con id {} actualizado correctamente", actualizado.getId());
        return estadoReservaMapper.toDTO(actualizado);
    }

    public void delete(Integer id) {
        log.info("Eliminando estado de reserva con id {}", id);
        if (!estadoReservaRepository.existsById(id)) {
            log.error("Estado de reserva no encontrado con id {} al intentar eliminar", id);
            throw new ResourceNotFoundException("Estado de reserva no encontrado");
        }
        estadoReservaRepository.deleteById(id);
        log.info("Estado de reserva con id {} eliminado correctamente", id);
    }
}

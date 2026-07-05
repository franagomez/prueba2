package com.msreservas.ms_reservas.Service;

import com.msreservas.ms_reservas.DTO.ReservaDTO;
import com.msreservas.ms_reservas.DTO.ReservaRequestDTO;
import com.msreservas.ms_reservas.Exception.ResourceNotFoundException;
import com.msreservas.ms_reservas.Mapper.ReservaMapper;
import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Model.Reserva;
import com.msreservas.ms_reservas.Repository.EstadoReservaRepository;
import com.msreservas.ms_reservas.Repository.ReservaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final EstadoReservaRepository estadoReservaRepository;

    public ReservaService(ReservaRepository reservaRepository, ReservaMapper reservaMapper,
                           EstadoReservaRepository estadoReservaRepository) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
        this.estadoReservaRepository = estadoReservaRepository;
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> findAll() {
        log.info("Listando reservas");
        List<Reserva> reservas = reservaRepository.findAll();
        List<ReservaDTO> listaDTO = new ArrayList<>();
        for (Reserva reserva : reservas) {
            listaDTO.add(reservaMapper.toReservaDTO(reserva));
        }
        log.info("Se encontraron {} reservas", listaDTO.size());
        return listaDTO;
    }

    @Transactional(readOnly = true)
    public ReservaDTO findById(Integer id) {
        log.info("Buscando reserva con id {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reserva no encontrada con id {}", id);
                    return new ResourceNotFoundException("Reserva no encontrada");
                });
        return reservaMapper.toReservaDTO(reserva);
    }

    public ReservaDTO save(ReservaRequestDTO dto) {
        log.info("Creando reserva para cliente id {}", dto.getClienteId());
        EstadoReserva estado = estadoReservaRepository.findById(dto.getEstadoReservaId())
                .orElseThrow(() -> {
                    log.error("Estado de reserva no encontrado con id {}", dto.getEstadoReservaId());
                    return new ResourceNotFoundException("Estado de reserva no encontrado");
                });

        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setEstadoReserva(estado);

        Reserva guardada = reservaRepository.save(reserva);
        log.info("Reserva guardada correctamente con id {}", guardada.getId());
        return reservaMapper.toReservaDTO(guardada);
    }

    public ReservaDTO update(Integer id, ReservaRequestDTO dto) {
        log.info("Actualizando reserva con id {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reserva no encontrada con id {} al intentar actualizar", id);
                    return new ResourceNotFoundException("Reserva no encontrada");
                });

        EstadoReserva estado = estadoReservaRepository.findById(dto.getEstadoReservaId())
                .orElseThrow(() -> {
                    log.error("Estado de reserva no encontrado con id {}", dto.getEstadoReservaId());
                    return new ResourceNotFoundException("Estado de reserva no encontrado");
                });

        reserva.setClienteId(dto.getClienteId());
        reserva.setNombreCliente(dto.getNombreCliente());
        reserva.setVehiculoId(dto.getVehiculoId());
        reserva.setCantidadDias(dto.getCantidadDias());
        reserva.setTotalReserva(dto.getTotalReserva());
        reserva.setActiva(dto.isActiva());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setEstadoReserva(estado);

        Reserva actualizada = reservaRepository.save(reserva);
        log.info("Reserva con id {} actualizada correctamente", actualizada.getId());
        return reservaMapper.toReservaDTO(actualizada);
    }

    public void delete(Integer id) {
        log.info("Eliminando reserva con id {}", id);
        if (!reservaRepository.existsById(id)) {
            log.error("Reserva no encontrada con id {} al intentar eliminar", id);
            throw new ResourceNotFoundException("Reserva no encontrada");
        }
        reservaRepository.deleteById(id);
        log.info("Reserva con id {} eliminada correctamente", id);
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> buscarActivasPorDiasMayor(Integer dias) {
        log.info("Buscando reservas activas con más de {} días", dias);
        List<Reserva> reservas = reservaRepository.findByActivaTrueAndCantidadDiasGreaterThan(dias);
        List<ReservaDTO> listaDTO = new ArrayList<>();
        for (Reserva reserva : reservas) {
            listaDTO.add(reservaMapper.toReservaDTO(reserva));
        }
        return listaDTO;
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> buscarDesdeFecha(LocalDate fecha) {
        log.info("Buscando reservas desde la fecha {}", fecha);
        List<Reserva> reservas = reservaRepository.buscarReservasDesdeFecha(fecha);
        List<ReservaDTO> listaDTO = new ArrayList<>();
        for (Reserva reserva : reservas) {
            listaDTO.add(reservaMapper.toReservaDTO(reserva));
        }
        return listaDTO;
    }
}

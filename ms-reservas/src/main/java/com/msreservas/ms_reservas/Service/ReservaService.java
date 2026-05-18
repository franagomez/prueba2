package com.msreservas.ms_reservas.Service;


import com.msreservas.ms_reservas.DTO.ReservaDTO;
import com.msreservas.ms_reservas.DTO.ReservaRequestDTO;
import com.msreservas.ms_reservas.Exception.ResourceNotFoundException;
import com.msreservas.ms_reservas.Mapper.ReservaMapper;
import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Model.Reserva;
import com.msreservas.ms_reservas.Repository.EstadoReservaRepository;
import com.msreservas.ms_reservas.Repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private EstadoReservaRepository estadoReservaRepository;

    // Listar reservas
    public List<ReservaDTO> findAll(){

        List<Reserva> reservas = reservaRepository.findAll();

        List<ReservaDTO> listaDTO = new ArrayList<>();

        for (Reserva reserva : reservas) {
            listaDTO.add(reservaMapper.toReservaDTO(reserva));
        }

        return listaDTO;
    }

    // Buscar reserva por id
    public ReservaDTO findById(Integer id){

        Reserva reserva = reservaRepository.findById(id).orElse(null);

        if (reserva == null){
            throw new ResourceNotFoundException("Reserva no encotrada");
        }

        return reservaMapper.toReservaDTO(reserva);
    }

    // Crear reserva
    public ReservaDTO save(ReservaRequestDTO dto){

        EstadoReserva estado = estadoReservaRepository
                .findById(dto.getEstadoReservaId())
                .orElse(null);

        if(estado == null){
            throw new ResourceNotFoundException("Estado de reserva no encontrado");
        }

        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setEstadoReserva(estado);

        Reserva guardada = reservaRepository.save(reserva);

        return reservaMapper.toReservaDTO(guardada);
    }

    // Actuallizar reserva
    public ReservaDTO update(Integer id, ReservaRequestDTO dto){

        Reserva reserva = reservaRepository.findById(id).orElse(null);

        if(reserva == null){
            throw new ResourceNotFoundException("Reserva no encontrada");
        }

        EstadoReserva estado = estadoReservaRepository
                .findById(dto.getEstadoReservaId())
                .orElse(null);

        if(estado == null){
            throw new ResourceNotFoundException("Estado de reserva no encontrado");
        }

        reserva.setNombreCliente(dto.getNombreCliente());
        reserva.setVehiculoId(dto.getVehiculoId());
        reserva.setCantidadDias(dto.getCantidadDias());
        reserva.setTotalReserva(dto.getTotalReserva());
        reserva.setActiva(dto.isActiva());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setEstadoReserva(estado);

        Reserva actualizada = reservaRepository.save(reserva);

        return reservaMapper.toReservaDTO(actualizada);
    }

    // Eliminar reserva
    public boolean delete(Integer id){

        if(reservaRepository.existsById(id)){
            reservaRepository.deleteById(id);
            return true;
        }

        return false;
    }

    // Reservas activas con dias mayor al indicado
    public List<ReservaDTO> buscarActivasPorDiasMayor(Integer dias){

        List<Reserva> reservas = reservaRepository
                .findByActivaTrueAndCantidadDiasGreaterThan(dias);

        List<ReservaDTO> listaDTO = new ArrayList<>();

        for(Reserva reserva : reservas){
            listaDTO.add(reservaMapper.toReservaDTO(reserva));
        }

        return listaDTO;
    }





}

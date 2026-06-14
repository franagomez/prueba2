package com.msreservas.ms_reservas.Service;


import com.msreservas.ms_reservas.DTO.EstadoReservaDTO;
import com.msreservas.ms_reservas.DTO.EstadoReservaRequestDTO;
import com.msreservas.ms_reservas.Exception.ResourceNotFoundException;
import com.msreservas.ms_reservas.Mapper.EstadoReservaMapper;
import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Repository.EstadoReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
@Transactional
public class EstadoReservaService {

    @Autowired
    private EstadoReservaRepository estadoReservaRepository;

    @Autowired
    private EstadoReservaMapper estadoReservaMapper;

    // Listar estados de reserva
    public List<EstadoReservaDTO> findAll(){

        List<EstadoReserva> estados = estadoReservaRepository.findAll();
        List<EstadoReservaDTO> listaDTO = new ArrayList<>();

        for (EstadoReserva estado : estados){

            listaDTO.add(estadoReservaMapper.toDTO(estado));
        }

        return listaDTO;
    }

    // Buscar estado por id
    public EstadoReservaDTO findById(Integer id){

        EstadoReserva estado = estadoReservaRepository.findById(id).orElse(null);

        if (estado == null){
            throw new ResourceNotFoundException("Estado de reserva no encontrado");
        }

        return estadoReservaMapper.toDTO(estado);
    }

    // Crear estado

    public EstadoReservaDTO save(EstadoReservaRequestDTO dto){

        EstadoReserva estado = estadoReservaMapper.toEntity(dto);

        EstadoReserva guardado = estadoReservaRepository.save(estado);

        return estadoReservaMapper.toDTO(guardado);
    }

    // Actualizar estado de reserva

    public EstadoReservaDTO update(Integer id, EstadoReservaRequestDTO dto){

        EstadoReserva estado = estadoReservaRepository.findById(id).orElse(null);

        if (estado == null){
            throw new ResourceNotFoundException("Estado de reserva no encontrado");
        }

        estado.setNombre(dto.getNombre());
        estado.setDescripcion(dto.getDescripcion());
        estado.setPrioridad(dto.getPrioridad());
        estado.setActivo(dto.isActivo());
        estado.setFechaCreacion(dto.getFechaCreacion());

        EstadoReserva actualizado = estadoReservaRepository.save(estado);

        return estadoReservaMapper.toDTO(actualizado);
    }

    // Eliminar estado de reserva
    public boolean delete(Integer id){

        if (estadoReservaRepository.existsById(id)){
            estadoReservaRepository.deleteById(id);
            return true;
        }

        return false;
    }

}

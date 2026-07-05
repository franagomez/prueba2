package com.msclientes.ms_clientes.Service;

import com.msclientes.ms_clientes.DTO.DireccionDTO;
import com.msclientes.ms_clientes.DTO.DireccionRequestDTO;
import com.msclientes.ms_clientes.Exception.ResourceNotFoundException;
import com.msclientes.ms_clientes.Mapper.DireccionMapper;
import com.msclientes.ms_clientes.Model.Cliente;
import com.msclientes.ms_clientes.Model.Direccion;
import com.msclientes.ms_clientes.Repository.ClienteRepository;
import com.msclientes.ms_clientes.Repository.DireccionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class DireccionService {

    private final DireccionRepository direccionRepository;
    private final DireccionMapper direccionMapper;
    private final ClienteRepository clienteRepository;

    public DireccionService(DireccionRepository direccionRepository,
                            DireccionMapper direccionMapper,
                            ClienteRepository clienteRepository) {
        this.direccionRepository = direccionRepository;
        this.direccionMapper = direccionMapper;
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<DireccionDTO> findAll() {
        log.info("Listando todas las direcciones");

        List<Direccion> direcciones = direccionRepository.findAll();
        List<DireccionDTO> listaDTO = new ArrayList<>();
        for (Direccion direccion : direcciones) {
            listaDTO.add(direccionMapper.toDireccionDTO(direccion));
        }

        log.info("Se encontraron {} direcciones", listaDTO.size());
        return listaDTO;
    }

    @Transactional(readOnly = true)
    public DireccionDTO findById(Integer id) {
        log.info("Buscando dirección con id: {}", id);

        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Dirección no encontrada con id: {}", id);
                    return new ResourceNotFoundException("Dirección no encontrada");
                });

        return direccionMapper.toDireccionDTO(direccion);
    }

    public DireccionDTO save(DireccionRequestDTO direccionDTO) {
        log.info("Guardando nueva dirección para cliente id: {}", direccionDTO.getClienteId());

        Cliente cliente = clienteRepository.findById(direccionDTO.getClienteId())
                .orElseThrow(() -> {
                    log.error("Cliente no encontrado con id: {}", direccionDTO.getClienteId());
                    return new ResourceNotFoundException("Cliente no encontrado");
                });

        Direccion direccion = direccionMapper.toEntity(direccionDTO);
        direccion.setCliente(cliente);

        Direccion guardada = direccionRepository.save(direccion);
        log.info("Dirección guardada correctamente con id: {}", guardada.getId());

        return direccionMapper.toDireccionDTO(guardada);
    }

    public DireccionDTO update(Integer id, DireccionRequestDTO direccionDTO) {
        log.info("Actualizando dirección con id: {}", id);

        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Dirección no encontrada con id: {} al intentar actualizar", id);
                    return new ResourceNotFoundException("Dirección no encontrada");
                });

        Cliente cliente = clienteRepository.findById(direccionDTO.getClienteId())
                .orElseThrow(() -> {
                    log.error("Cliente no encontrado con id: {}", direccionDTO.getClienteId());
                    return new ResourceNotFoundException("Cliente no encontrado");
                });

        direccion.setCalle(direccionDTO.getCalle());
        direccion.setNumero(direccionDTO.getNumero());
        direccion.setComuna(direccionDTO.getComuna());
        direccion.setCiudad(direccionDTO.getCiudad());
        direccion.setCodigoPostal(direccionDTO.getCodigoPostal());
        direccion.setPrincipal(direccionDTO.isPrincipal());
        direccion.setFechaRegistro(direccionDTO.getFechaRegistro());
        direccion.setCliente(cliente);

        Direccion actualizada = direccionRepository.save(direccion);
        log.info("Dirección con id: {} actualizada correctamente", actualizada.getId());

        return direccionMapper.toDireccionDTO(actualizada);
    }

    public void delete(Integer id) {
        log.info("Eliminando dirección con id: {}", id);

        if (!direccionRepository.existsById(id)) {
            log.error("Dirección no encontrada con id: {} al intentar eliminar", id);
            throw new ResourceNotFoundException("Dirección no encontrada");
        }

        direccionRepository.deleteById(id);
        log.info("Dirección con id: {} eliminada correctamente", id);
    }
}
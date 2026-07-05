package com.msclientes.ms_clientes.Service;

import lombok.extern.slf4j.Slf4j;
import com.msclientes.ms_clientes.DTO.ClienteDTO;
import com.msclientes.ms_clientes.DTO.ClienteRequestDTO;
import com.msclientes.ms_clientes.Exception.ResourceNotFoundException;
import com.msclientes.ms_clientes.Model.Cliente;
import com.msclientes.ms_clientes.Mapper.ClienteMapper;
import com.msclientes.ms_clientes.Repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@Slf4j
@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        log.info("Listando todos los clientes");
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> listaDTO = new ArrayList<>();
        for (Cliente cliente : clientes) {
            listaDTO.add(clienteMapper.toDTO(cliente));
        }
        log.info("Se encontraron {} clientes", listaDTO.size());
        return listaDTO;
    }

    @Transactional(readOnly = true)
    public ClienteDTO findById(Integer id) {
        log.info("Buscando cliente con id: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cliente no encontrado con id: {}", id);
                    return new ResourceNotFoundException("Cliente no encontrado");
                });
        return clienteMapper.toDTO(cliente);
    }

    public ClienteDTO save(ClienteRequestDTO dto) {
        log.info("Guardando nuevo cliente con email: {}", dto.getEmail());
        Cliente cliente = clienteMapper.toEntity(dto);
        Cliente guardado = clienteRepository.save(cliente);
        log.info("Cliente guardado correctamente con id: {}", guardado.getId());
        return clienteMapper.toDTO(guardado);
    }

    public ClienteDTO update(Integer id, ClienteRequestDTO dto) {
        log.info("Actualizando cliente con id: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cliente no encontrado con id: {} al intentar actualizar", id);
                    return new ResourceNotFoundException("Cliente no encontrado");
                });

        cliente.setRun(dto.getRun());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setPuntosCliente(dto.getPuntosCliente());
        cliente.setActivo(dto.isActivo());
        cliente.setFechaRegistro(dto.getFechaRegistro());

        Cliente actualizado = clienteRepository.save(cliente);
        log.info("Cliente con id: {} actualizado correctamente", actualizado.getId());
        return clienteMapper.toDTO(actualizado);
    }

    public void delete(Integer id) {
        log.info("Eliminando cliente con id: {}", id);
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            log.info("Cliente con id: {} eliminado correctamente", id);
            return;
        }
        log.error("Cliente no encontrado con id: {} al intentar eliminar", id);
        throw new ResourceNotFoundException("Cliente no encontrado");
    }
}
package com.msclientes.ms_clientes.Service;

import lombok.extern.slf4j.Slf4j;
import com.msclientes.ms_clientes.DTO.ClienteDTO;
import com.msclientes.ms_clientes.DTO.ClienteRequestDTO;
import com.msclientes.ms_clientes.Exception.ResourceNotFoundException;
import com.msclientes.ms_clientes.Model.Cliente;
import com.msclientes.ms_clientes.Mapper.ClienteMapper;
import com.msclientes.ms_clientes.Repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Slf4j
@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    // Listar a todos los clientes
    public List<ClienteDTO> findAll(){

        log.info("Listando todos los clientes");

        List<Cliente> clientes = clienteRepository.findAll();

        List<ClienteDTO> listaDTO = new ArrayList<>();

        for (Cliente cliente : clientes){
            listaDTO.add(clienteMapper.toDTO(cliente));

        }

        return listaDTO;
    }

    //Buscando clientes por ID
    public ClienteDTO findById(Integer id){

        log.info("Buscando cliente con id: {}", id);

        Cliente cliente = clienteRepository.findById(id).orElse(null);

        if (cliente == null){
            log.error("Cliente no encontrado con id: {}", id);
            throw new ResourceNotFoundException("Cliente no encontrado");

        }

        return clienteMapper.toDTO(cliente);
    }

    // Crear cliente
    public ClienteDTO save(ClienteRequestDTO dto){

        log.info("Guardando nuevo cliente con email: {}", dto.getEmail());

        Cliente cliente = clienteMapper.toEntity(dto);

        Cliente guardado = clienteRepository.save(cliente);

        return clienteMapper.toDTO(guardado);
    }


    // Actualizar cliente

    public ClienteDTO update(Integer id, ClienteRequestDTO dto){

        Cliente cliente = clienteRepository.findById(id).get();

        cliente.setRun(dto.getRun());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setPuntosCliente(dto.getPuntosCliente());
        cliente.setActivo(dto.isActivo());
        cliente.setFechaRegistro(dto.getFechaRegistro());

        Cliente actualizado = clienteRepository.save(cliente);

        return clienteMapper.toDTO(actualizado);
    }

    // Eliminar cliente

    public boolean delete(Integer id){

        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }

        return false;
    }





}

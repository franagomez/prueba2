package com.msclientes.ms_clientes.Mapper;

import com.msclientes.ms_clientes.DTO.ClienteDTO;
import com.msclientes.ms_clientes.DTO.ClienteRequestDTO;
import com.msclientes.ms_clientes.Model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public Cliente toEntity(ClienteRequestDTO DTO) {
        Cliente cliente = new Cliente();
        cliente.setRun(DTO.getRun());
        cliente.setNombre(DTO.getNombre());
        cliente.setApellido(DTO.getApellido());
        cliente.setEmail(DTO.getEmail());
        cliente.setTelefono(DTO.getTelefono());
        cliente.setPuntosCliente(DTO.getPuntosCliente());
        cliente.setActivo(DTO.isActivo());
        cliente.setFechaRegistro(DTO.getFechaRegistro());

        return cliente;
    }

    public ClienteDTO toDTO(Cliente cliente) {

        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setRun(cliente.getRun());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setPuntosCliente(cliente.getPuntosCliente());
        dto.setActivo(cliente.isActivo());
        dto.setFechaRegistro(cliente.getFechaRegistro());

        return dto;
    }
}

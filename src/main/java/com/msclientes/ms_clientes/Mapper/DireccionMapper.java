package com.msclientes.ms_clientes.Mapper;


import com.msclientes.ms_clientes.DTO.DireccionDTO;
import com.msclientes.ms_clientes.DTO.DireccionRequestDTO;
import com.msclientes.ms_clientes.Model.Cliente;
import com.msclientes.ms_clientes.Model.Direccion;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper {

    public DireccionDTO toDireccionDTO(Direccion direccion) {

        DireccionDTO direccionDTO = new DireccionDTO();

        direccionDTO.setId(direccion.getId());
        direccionDTO.setCalle(direccion.getCalle());
        direccionDTO.setNumero(direccion.getNumero());
        direccionDTO.setComuna(direccion.getComuna());
        direccionDTO.setCiudad(direccion.getCiudad());
        direccionDTO.setCodigoPostal(direccion.getCodigoPostal());
        direccionDTO.setPrincipal(direccion.isPrincipal());
        direccionDTO.setFechaRegistro(direccion.getFechaRegistro());

        if(direccion.getCliente() != null) {
            direccionDTO.setClienteId(direccion.getCliente().getId());
        }

        return direccionDTO;
    }


    public Direccion toEntity(DireccionRequestDTO dto){
        Direccion direccion = new Direccion();

        direccion.setCalle(dto.getCalle());
        direccion.setNumero(dto.getNumero());
        direccion.setComuna(dto.getComuna());
        direccion.setCiudad(dto.getCiudad());
        direccion.setCodigoPostal(dto.getCodigoPostal());
        direccion.setPrincipal(dto.isPrincipal());
        direccion.setFechaRegistro(dto.getFechaRegistro());

        Cliente cliente = new Cliente();
        cliente.setId(dto.getClienteId());

        direccion.setCliente(cliente);

        return direccion;

    }
}

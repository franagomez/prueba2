package com.msclientes.ms_clientes.Service;


import com.msclientes.ms_clientes.DTO.DireccionDTO;
import com.msclientes.ms_clientes.DTO.DireccionRequestDTO;
import com.msclientes.ms_clientes.Exception.ResourceNotFoundException;
import com.msclientes.ms_clientes.Mapper.DireccionMapper;
import com.msclientes.ms_clientes.Model.Cliente;
import com.msclientes.ms_clientes.Model.Direccion;
import com.msclientes.ms_clientes.Repository.ClienteRepository;
import com.msclientes.ms_clientes.Repository.DireccionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private DireccionMapper direccionMapper;

    @Autowired
    private ClienteRepository clienteRepository;


    // Listar todas las direcciones
    public List<DireccionDTO>  findAll(){

        log.info("Listando todas las direcciones");

        List<Direccion> direcciones = direccionRepository.findAll();
        List<DireccionDTO> listaDTO = new ArrayList<>();
        for(Direccion direccion : direcciones){
            listaDTO.add(direccionMapper.toDireccionDTO(direccion));
        }

        return listaDTO;
    }

    // Buscar direccion por id
    public DireccionDTO findById(Integer id){

        log.info("Buscando dirección con id: {}", id);

        Direccion direccion = direccionRepository.findById(id).orElse(null);

        if(direccion == null) {
            log.error("Dirección no encontrada con id: {}", id);
            throw new ResourceNotFoundException("Dirección no encontrada");
        }

        return direccionMapper.toDireccionDTO(direccion);
    }

    //Crear direccion
    public DireccionDTO save(DireccionRequestDTO direccionDTO){

        log.info("Guardando nueva dirección para cliente id: {}", direccionDTO.getClienteId());

        // obtenemos el tipo de dato del id y al cliente
        Cliente cliente = clienteRepository.findById(direccionDTO.getClienteId()).orElse(null);

        if(cliente == null) {
            log.error("Cliente no encontrado con id: {}", direccionDTO.getClienteId());
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        Direccion direccion = direccionMapper.toEntity(direccionDTO);
        direccion.setCliente(cliente);

        Direccion guardada = direccionRepository.save(direccion);

        return direccionMapper.toDireccionDTO(guardada);
    }

    // Actualizar direccion
    public DireccionDTO update(Integer id, DireccionRequestDTO direccionDTO){

        log.info("Actualizando dirección con id: {}", id);

        Direccion direccion = direccionRepository.findById(id).orElse(null);

        if(direccion == null) {
            if (log.isErrorEnabled()) {
                log.error("Direccion no encontrada con id: {}", id);
            } throw new ResourceNotFoundException("Direccion no encontrada");
        }

        Cliente cliente = clienteRepository.findById(direccionDTO.getClienteId()).orElse(null);
        if (cliente == null) {
            log.error("Cliente no encontrado con id: {}", direccionDTO.getClienteId());

            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        direccion.setCalle(direccionDTO.getCalle());
        direccion.setNumero(direccionDTO.getNumero());
        direccion.setComuna( direccionDTO.getComuna());
        direccion.setCiudad(direccionDTO.getCiudad());
        direccion.setCodigoPostal(direccionDTO.getCodigoPostal());
        direccion.setPrincipal(direccionDTO.isPrincipal());
        direccion.setFechaRegistro(direccionDTO.getFechaRegistro());
        direccion.setCliente(cliente);

        Direccion actualizada = direccionRepository.save(direccion);

        return direccionMapper.toDireccionDTO(actualizada);
    }

    // Eliminar direccion
    public boolean delete(Integer id){

        if (direccionRepository.existsById(id)) {
            direccionRepository.deleteById(id);
            return true;
        }

        return false;
    }

}

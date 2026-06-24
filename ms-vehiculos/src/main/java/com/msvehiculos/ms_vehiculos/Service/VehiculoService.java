package com.msvehiculos.ms_vehiculos.Service;


import com.msvehiculos.ms_vehiculos.DTO.VehiculoDTO;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoRequestDTO;
import com.msvehiculos.ms_vehiculos.Exception.ResourceNotFoundException;
import com.msvehiculos.ms_vehiculos.Mapper.VehiculoMapper;
import com.msvehiculos.ms_vehiculos.Model.Categoria;
import com.msvehiculos.ms_vehiculos.Model.Vehiculo;
import com.msvehiculos.ms_vehiculos.Repository.CategoriaRepository;
import com.msvehiculos.ms_vehiculos.Repository.VehiculoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VehiculoService {

    private static final Logger log = LoggerFactory.getLogger(VehiculoService.class);

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private VehiculoMapper vehiculoMapper;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Listar Vehiculos
    public List<VehiculoDTO> findAll() {

        log.info("Listando vehículos");

        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        List<VehiculoDTO> listaDTO = new ArrayList<>();

        for (Vehiculo vehiculo : vehiculos) {
            listaDTO.add(vehiculoMapper.toDTO(vehiculo));
        }

        return listaDTO;
    }

    // Buscar vehiculo por Id
    public VehiculoDTO findById(Integer id) {

        log.info("Buscando vehículo con id {}", id);

        Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);

        if (vehiculo == null){
            throw new ResourceNotFoundException("Vehiculo no encontrado");
        }

        return vehiculoMapper.toDTO(vehiculo);
    }

    // Crear vehiculo
    public VehiculoDTO save(VehiculoRequestDTO dto) {

        log.info("Creando vehículo con patente {}", dto.getPatente());

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElse(null);

        if (categoria == null){
            throw new ResourceNotFoundException("Categoria no encontrada");
        }

        Vehiculo vehiculo = vehiculoMapper.toEntity(dto);
        vehiculo.setCategoria(categoria);

        Vehiculo guardado = vehiculoRepository.save(vehiculo);

        return vehiculoMapper.toDTO(guardado);
    }

    //Actualizar vehiculo
    public VehiculoDTO update(Integer id, VehiculoRequestDTO dto){

        log.info("Actualizando vehículo con id {}", id);

        Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);

        if (vehiculo == null){
            throw new ResourceNotFoundException("Vehiculo no encontrado");
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElse(null);

        if (categoria == null){
            throw new ResourceNotFoundException("Categoria no encontrada");
        }

        vehiculo.setPatente(dto.getPatente());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setAnio(dto.getAnio());
        vehiculo.setPrecioArriendoDiario(dto.getPrecioArriendoDiario());
        vehiculo.setDisponible(dto.isDisponible());
        vehiculo.setFechaRegistro(dto.getFechaRegistro());
        vehiculo.setCategoria(categoria);

        Vehiculo actualizado = vehiculoRepository.save(vehiculo);

        return vehiculoMapper.toDTO(actualizado);
    }

    // Eliminar vehiculo
    public boolean delete(Integer id){

        log.info("Eliminando vehículo con id {}", id);

        if(vehiculoRepository.existsById(id)){
            vehiculoRepository.deleteById(id);
            return true;
        }

        return false;
    }

    // Query Method para vehiculos disponibles con precio
    // menor al indicado
    public List<VehiculoDTO> buscarDisponiblesPorPrecioMenor(Double precio){

        log.info("Buscando vehículos disponibles con precio menor a {}", precio);

        List<Vehiculo> vehiculos = vehiculoRepository.findByDisponibleTrueAndPrecioArriendoDiarioLessThan(precio);

        List<VehiculoDTO> listaDTO = new ArrayList<>();

        for (Vehiculo vehiculo : vehiculos){
            listaDTO.add(vehiculoMapper.toDTO(vehiculo));
        }

        return listaDTO;
    }
}

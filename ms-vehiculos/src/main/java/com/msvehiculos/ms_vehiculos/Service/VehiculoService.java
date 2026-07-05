package com.msvehiculos.ms_vehiculos.Service;

import com.msvehiculos.ms_vehiculos.DTO.VehiculoDTO;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoRequestDTO;
import com.msvehiculos.ms_vehiculos.Exception.ResourceNotFoundException;
import com.msvehiculos.ms_vehiculos.Mapper.VehiculoMapper;
import com.msvehiculos.ms_vehiculos.Model.Categoria;
import com.msvehiculos.ms_vehiculos.Model.Vehiculo;
import com.msvehiculos.ms_vehiculos.Repository.CategoriaRepository;
import com.msvehiculos.ms_vehiculos.Repository.VehiculoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;
    private final CategoriaRepository categoriaRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository, VehiculoMapper vehiculoMapper,
                            CategoriaRepository categoriaRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoMapper = vehiculoMapper;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findAll() {
        log.info("Listando vehículos");
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        List<VehiculoDTO> listaDTO = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            listaDTO.add(vehiculoMapper.toDTO(vehiculo));
        }
        log.info("Se encontraron {} vehículos", listaDTO.size());
        return listaDTO;
    }

    @Transactional(readOnly = true)
    public VehiculoDTO findById(Integer id) {
        log.info("Buscando vehículo con id {}", id);
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Vehículo no encontrado con id {}", id);
                    return new ResourceNotFoundException("Vehiculo no encontrado");
                });
        return vehiculoMapper.toDTO(vehiculo);
    }

    public VehiculoDTO save(VehiculoRequestDTO dto) {
        log.info("Creando vehículo con patente {}", dto.getPatente());
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada con id {}", dto.getCategoriaId());
                    return new ResourceNotFoundException("Categoria no encontrada");
                });

        Vehiculo vehiculo = vehiculoMapper.toEntity(dto);
        vehiculo.setCategoria(categoria);

        Vehiculo guardado = vehiculoRepository.save(vehiculo);
        log.info("Vehículo guardado correctamente con id {}", guardado.getId());
        return vehiculoMapper.toDTO(guardado);
    }

    public VehiculoDTO update(Integer id, VehiculoRequestDTO dto) {
        log.info("Actualizando vehículo con id {}", id);
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Vehículo no encontrado con id {} al intentar actualizar", id);
                    return new ResourceNotFoundException("Vehiculo no encontrado");
                });

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada con id {}", dto.getCategoriaId());
                    return new ResourceNotFoundException("Categoria no encontrada");
                });

        vehiculo.setPatente(dto.getPatente());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setAnio(dto.getAnio());
        vehiculo.setPrecioArriendoDiario(dto.getPrecioArriendoDiario());
        vehiculo.setDisponible(dto.isDisponible());
        vehiculo.setFechaRegistro(dto.getFechaRegistro());
        vehiculo.setCategoria(categoria);

        Vehiculo actualizado = vehiculoRepository.save(vehiculo);
        log.info("Vehículo con id {} actualizado correctamente", actualizado.getId());
        return vehiculoMapper.toDTO(actualizado);
    }

    public void delete(Integer id) {
        log.info("Eliminando vehículo con id {}", id);
        if (!vehiculoRepository.existsById(id)) {
            log.error("Vehículo no encontrado con id {} al intentar eliminar", id);
            throw new ResourceNotFoundException("Vehiculo no encontrado");
        }
        vehiculoRepository.deleteById(id);
        log.info("Vehículo con id {} eliminado correctamente", id);
    }

    @Transactional(readOnly = true)
    public List<VehiculoDTO> buscarDisponiblesPorPrecioMenor(Double precio) {
        log.info("Buscando vehículos disponibles con precio menor a {}", precio);
        List<Vehiculo> vehiculos = vehiculoRepository.findByDisponibleTrueAndPrecioArriendoDiarioLessThan(precio);
        List<VehiculoDTO> listaDTO = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            listaDTO.add(vehiculoMapper.toDTO(vehiculo));
        }
        return listaDTO;
    }
}

package com.msvehiculos.ms_vehiculos.Service;

import com.msvehiculos.ms_vehiculos.DTO.VehiculoDTO;
import com.msvehiculos.ms_vehiculos.DTO.VehiculoRequestDTO;
import com.msvehiculos.ms_vehiculos.Exception.ResourceNotFoundException;
import com.msvehiculos.ms_vehiculos.Mapper.VehiculoMapper;
import com.msvehiculos.ms_vehiculos.Model.Categoria;
import com.msvehiculos.ms_vehiculos.Model.Vehiculo;
import com.msvehiculos.ms_vehiculos.Repository.CategoriaRepository;
import com.msvehiculos.ms_vehiculos.Repository.VehiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private VehiculoMapper vehiculoMapper;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @Test
    void findAll_debeRetornarListaDeVehiculos() {
        // Given
        Vehiculo vehiculo = crearVehiculo();
        VehiculoDTO vehiculoDTO = crearVehiculoDTO();

        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        // When
        List<VehiculoDTO> resultado = vehiculoService.findAll();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Toyota", resultado.get(0).getMarca());

        verify(vehiculoRepository, times(1)).findAll();
        verify(vehiculoMapper, times(1)).toDTO(vehiculo);
    }

    @Test
    void findAll_cuandoNoHayVehiculos_debeRetornarListaVacia() {
        // Given
        when(vehiculoRepository.findAll()).thenReturn(List.of());

        // When
        List<VehiculoDTO> resultado = vehiculoService.findAll();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(vehiculoRepository, times(1)).findAll();
        verifyNoInteractions(vehiculoMapper);
    }

    @Test
    void findById_cuandoExiste_debeRetornarVehiculoDTO() {
        // Given
        Vehiculo vehiculo = crearVehiculo();
        VehiculoDTO vehiculoDTO = crearVehiculoDTO();

        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        // When
        VehiculoDTO resultado = vehiculoService.findById(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("AA1122", resultado.getPatente());

        verify(vehiculoRepository, times(1)).findById(1);
        verify(vehiculoMapper, times(1)).toDTO(vehiculo);
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        when(vehiculoRepository.findById(99)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculoService.findById(99)
        );

        assertEquals("Vehiculo no encontrado", exception.getMessage());

        verify(vehiculoRepository, times(1)).findById(99);
        verifyNoInteractions(vehiculoMapper);
    }

    @Test
    void save_cuandoCategoriaExiste_debeGuardarVehiculoYRetornarDTO() {
        // Given
        VehiculoRequestDTO requestDTO = crearVehiculoRequestDTO();
        Categoria categoria = crearCategoria();
        Vehiculo vehiculo = crearVehiculo();
        VehiculoDTO vehiculoDTO = crearVehiculoDTO();

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(vehiculoMapper.toEntity(requestDTO)).thenReturn(vehiculo);
        when(vehiculoRepository.save(vehiculo)).thenReturn(vehiculo);
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        // When
        VehiculoDTO resultado = vehiculoService.save(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("AA1122", resultado.getPatente());

        verify(categoriaRepository, times(1)).findById(1);
        verify(vehiculoMapper, times(1)).toEntity(requestDTO);
        verify(vehiculoRepository, times(1)).save(vehiculo);
        verify(vehiculoMapper, times(1)).toDTO(vehiculo);
    }

    @Test
    void save_cuandoCategoriaNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        VehiculoRequestDTO requestDTO = crearVehiculoRequestDTO();
        when(categoriaRepository.findById(1)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculoService.save(requestDTO)
        );

        assertEquals("Categoria no encontrada", exception.getMessage());

        verify(categoriaRepository, times(1)).findById(1);
        verifyNoInteractions(vehiculoMapper);
        verify(vehiculoRepository, never()).save(any());
    }

    @Test
    void update_cuandoVehiculoYCategoriaExisten_debeActualizarYRetornarDTO() {
        // Given
        VehiculoRequestDTO requestDTO = crearVehiculoRequestDTO();
        Vehiculo vehiculoExistente = crearVehiculo();
        Categoria categoria = crearCategoria();
        VehiculoDTO vehiculoActualizadoDTO = crearVehiculoDTO();

        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculoExistente));
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(vehiculoRepository.save(vehiculoExistente)).thenReturn(vehiculoExistente);
        when(vehiculoMapper.toDTO(vehiculoExistente)).thenReturn(vehiculoActualizadoDTO);

        // When
        VehiculoDTO resultado = vehiculoService.update(1, requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("AA1122", resultado.getPatente());

        verify(vehiculoRepository, times(1)).findById(1);
        verify(categoriaRepository, times(1)).findById(1);
        verify(vehiculoRepository, times(1)).save(vehiculoExistente);
        verify(vehiculoMapper, times(1)).toDTO(vehiculoExistente);
    }

    @Test
    void update_cuandoVehiculoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        VehiculoRequestDTO requestDTO = crearVehiculoRequestDTO();
        when(vehiculoRepository.findById(99)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculoService.update(99, requestDTO)
        );

        assertEquals("Vehiculo no encontrado", exception.getMessage());

        verify(vehiculoRepository, times(1)).findById(99);
        verifyNoInteractions(categoriaRepository);
        verify(vehiculoRepository, never()).save(any());
    }

    @Test
    void update_cuandoCategoriaNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        VehiculoRequestDTO requestDTO = crearVehiculoRequestDTO();
        Vehiculo vehiculoExistente = crearVehiculo();

        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculoExistente));
        when(categoriaRepository.findById(1)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculoService.update(1, requestDTO)
        );

        assertEquals("Categoria no encontrada", exception.getMessage());

        verify(vehiculoRepository, times(1)).findById(1);
        verify(categoriaRepository, times(1)).findById(1);
        verify(vehiculoRepository, never()).save(any());
    }

    @Test
    void delete_cuandoExiste_debeEliminarCorrectamente() {
        // Given
        when(vehiculoRepository.existsById(1)).thenReturn(true);

        // When
        vehiculoService.delete(1);

        // Then
        verify(vehiculoRepository, times(1)).existsById(1);
        verify(vehiculoRepository, times(1)).deleteById(1);
    }

    @Test
    void delete_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        when(vehiculoRepository.existsById(99)).thenReturn(false);

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculoService.delete(99)
        );

        assertEquals("Vehiculo no encontrado", exception.getMessage());

        verify(vehiculoRepository, times(1)).existsById(99);
        verify(vehiculoRepository, never()).deleteById(anyInt());
    }

    @Test
    void buscarDisponiblesPorPrecioMenor_debeRetornarListaDeVehiculos() {
        // Given
        Vehiculo vehiculo = crearVehiculo();
        VehiculoDTO vehiculoDTO = crearVehiculoDTO();

        when(vehiculoRepository.findByDisponibleTrueAndPrecioArriendoDiarioLessThan(35000.0))
                .thenReturn(List.of(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        // When
        List<VehiculoDTO> resultado = vehiculoService.buscarDisponiblesPorPrecioMenor(35000.0);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("AA1122", resultado.get(0).getPatente());

        verify(vehiculoRepository, times(1))
                .findByDisponibleTrueAndPrecioArriendoDiarioLessThan(35000.0);
        verify(vehiculoMapper, times(1)).toDTO(vehiculo);
    }

    private Categoria crearCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setNombre("SUV");
        categoria.setDescripcion("Vehiculos deportivos utilitarios");
        categoria.setCantidadVehiculos(3);
        categoria.setActiva(true);
        categoria.setFechaCreacion(LocalDate.of(2026, 6, 20));
        return categoria;
    }

    private Vehiculo crearVehiculo() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1);
        vehiculo.setPatente("AA1122");
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Rav4");
        vehiculo.setAnio(2023);
        vehiculo.setPrecioArriendoDiario(30000.0);
        vehiculo.setDisponible(true);
        vehiculo.setFechaRegistro(LocalDate.of(2026, 6, 20));
        vehiculo.setCategoria(crearCategoria());
        return vehiculo;
    }

    private VehiculoDTO crearVehiculoDTO() {
        return new VehiculoDTO(
                1,
                "AA1122",
                "Toyota",
                "Rav4",
                2023,
                30000.0,
                true,
                LocalDate.of(2026, 6, 20),
                1
        );
    }

    private VehiculoRequestDTO crearVehiculoRequestDTO() {
        return new VehiculoRequestDTO(
                "AA1122",
                "Toyota",
                "Rav4",
                2023,
                30000.0,
                true,
                LocalDate.of(2026, 6, 20),
                1
        );
    }

}

package com.msvehiculos.ms_vehiculos.Service;

import com.msvehiculos.ms_vehiculos.DTO.CategoriaDTO;
import com.msvehiculos.ms_vehiculos.DTO.CategoriaRequestDTO;
import com.msvehiculos.ms_vehiculos.Exception.ResourceNotFoundException;
import com.msvehiculos.ms_vehiculos.Mapper.CategoriaMapper;
import com.msvehiculos.ms_vehiculos.Model.Categoria;
import com.msvehiculos.ms_vehiculos.Repository.CategoriaRepository;
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

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void findAll_debeRetornarListaDeCategorias() {
        // Given
        Categoria categoria = crearCategoria();
        CategoriaDTO categoriaDTO = crearCategoriaDTO();

        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        when(categoriaMapper.toDTO(categoria)).thenReturn(categoriaDTO);

        // When
        List<CategoriaDTO> resultado = categoriaService.findAll();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("SUV", resultado.get(0).getNombre());

        verify(categoriaRepository, times(1)).findAll();
        verify(categoriaMapper, times(1)).toDTO(categoria);
    }

    @Test
    void findAll_cuandoNoHayCategorias_debeRetornarListaVacia() {
        // Given
        when(categoriaRepository.findAll()).thenReturn(List.of());

        // When
        List<CategoriaDTO> resultado = categoriaService.findAll();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(categoriaRepository, times(1)).findAll();
        verifyNoInteractions(categoriaMapper);
    }

    @Test
    void findById_cuandoExiste_debeRetornarCategoriaDTO() {
        // Given
        Categoria categoria = crearCategoria();
        CategoriaDTO categoriaDTO = crearCategoriaDTO();

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toDTO(categoria)).thenReturn(categoriaDTO);

        // When
        CategoriaDTO resultado = categoriaService.findById(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("SUV", resultado.getNombre());

        verify(categoriaRepository, times(1)).findById(1);
        verify(categoriaMapper, times(1)).toDTO(categoria);
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> categoriaService.findById(99)
        );

        assertEquals("Categoria no encontrada", exception.getMessage());

        verify(categoriaRepository, times(1)).findById(99);
        verifyNoInteractions(categoriaMapper);
    }

    @Test
    void save_debeGuardarCategoriaYRetornarDTO() {
        // Given
        CategoriaRequestDTO requestDTO = crearCategoriaRequestDTO();
        Categoria categoria = crearCategoria();
        CategoriaDTO categoriaDTO = crearCategoriaDTO();

        when(categoriaMapper.toEntity(requestDTO)).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        when(categoriaMapper.toDTO(categoria)).thenReturn(categoriaDTO);

        // When
        CategoriaDTO resultado = categoriaService.save(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("SUV", resultado.getNombre());
        assertTrue(resultado.isActiva());

        verify(categoriaMapper, times(1)).toEntity(requestDTO);
        verify(categoriaRepository, times(1)).save(categoria);
        verify(categoriaMapper, times(1)).toDTO(categoria);
    }

    @Test
    void update_cuandoExiste_debeActualizarCategoriaYRetornarDTO() {
        // Given
        CategoriaRequestDTO requestDTO = crearCategoriaRequestDTO();
        Categoria categoriaExistente = crearCategoria();
        CategoriaDTO categoriaActualizadaDTO = crearCategoriaDTO();

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaRepository.save(categoriaExistente)).thenReturn(categoriaExistente);
        when(categoriaMapper.toDTO(categoriaExistente)).thenReturn(categoriaActualizadaDTO);

        // When
        CategoriaDTO resultado = categoriaService.update(1, requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("SUV", resultado.getNombre());

        verify(categoriaRepository, times(1)).findById(1);
        verify(categoriaRepository, times(1)).save(categoriaExistente);
        verify(categoriaMapper, times(1)).toDTO(categoriaExistente);
    }

    @Test
    void update_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        CategoriaRequestDTO requestDTO = crearCategoriaRequestDTO();
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> categoriaService.update(99, requestDTO)
        );

        assertEquals("Categoria no encontrada", exception.getMessage());

        verify(categoriaRepository, times(1)).findById(99);
        verify(categoriaRepository, never()).save(any());
        verifyNoInteractions(categoriaMapper);
    }

    @Test
    void delete_cuandoExiste_debeEliminarCorrectamente() {
        // Given
        when(categoriaRepository.existsById(1)).thenReturn(true);

        // When
        categoriaService.delete(1);

        // Then
        verify(categoriaRepository, times(1)).existsById(1);
        verify(categoriaRepository, times(1)).deleteById(1);
    }

    @Test
    void delete_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        when(categoriaRepository.existsById(99)).thenReturn(false);

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> categoriaService.delete(99)
        );

        assertEquals("Categoria no encontrada", exception.getMessage());

        verify(categoriaRepository, times(1)).existsById(99);
        verify(categoriaRepository, never()).deleteById(anyInt());
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

    private CategoriaDTO crearCategoriaDTO() {
        return new CategoriaDTO(
                1,
                "SUV",
                "Vehiculos deportivos utilitarios",
                3,
                true,
                LocalDate.of(2026, 6, 20)
        );
    }

    private CategoriaRequestDTO crearCategoriaRequestDTO() {
        return new CategoriaRequestDTO(
                "SUV",
                "Vehiculos deportivos utilitarios",
                3,
                true,
                LocalDate.of(2026, 6, 20)
        );
    }

}

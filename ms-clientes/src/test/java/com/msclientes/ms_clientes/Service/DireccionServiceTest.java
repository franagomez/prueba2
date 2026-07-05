package com.msclientes.ms_clientes.Service;

import com.msclientes.ms_clientes.DTO.DireccionDTO;
import com.msclientes.ms_clientes.DTO.DireccionRequestDTO;
import com.msclientes.ms_clientes.Exception.ResourceNotFoundException;
import com.msclientes.ms_clientes.Mapper.DireccionMapper;
import com.msclientes.ms_clientes.Model.Cliente;
import com.msclientes.ms_clientes.Model.Direccion;
import com.msclientes.ms_clientes.Repository.ClienteRepository;
import com.msclientes.ms_clientes.Repository.DireccionRepository;
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

public class DireccionServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @Mock
    private DireccionMapper direccionMapper;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private DireccionService direccionService;

    @Test
    void findAll_debeRetornarListaDeDirecciones() {
        // Given
        Direccion direccion = crearDireccion();
        DireccionDTO direccionDTO = crearDireccionDTO();

        when(direccionRepository.findAll()).thenReturn(List.of(direccion));
        when(direccionMapper.toDireccionDTO(direccion)).thenReturn(direccionDTO);

        // When
        List<DireccionDTO> resultado = direccionService.findAll();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Av. Providencia", resultado.get(0).getCalle());

        verify(direccionRepository, times(1)).findAll();
        verify(direccionMapper, times(1)).toDireccionDTO(direccion);
    }

    @Test
    void findAll_cuandoNoHayDirecciones_debeRetornarListaVacia() {
        // Given
        when(direccionRepository.findAll()).thenReturn(List.of());

        // When
        List<DireccionDTO> resultado = direccionService.findAll();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(direccionRepository, times(1)).findAll();
        verifyNoInteractions(direccionMapper);
    }

    @Test
    void findById_cuandoExiste_debeRetornarDireccionDTO() {
        // Given
        Direccion direccion = crearDireccion();
        DireccionDTO direccionDTO = crearDireccionDTO();

        when(direccionRepository.findById(1)).thenReturn(Optional.of(direccion));
        when(direccionMapper.toDireccionDTO(direccion)).thenReturn(direccionDTO);

        // When
        DireccionDTO resultado = direccionService.findById(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Providencia", resultado.getComuna());

        verify(direccionRepository, times(1)).findById(1);
        verify(direccionMapper, times(1)).toDireccionDTO(direccion);
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        when(direccionRepository.findById(99)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> direccionService.findById(99)
        );

        assertEquals("Dirección no encontrada", exception.getMessage());

        verify(direccionRepository, times(1)).findById(99);
        verifyNoInteractions(direccionMapper);
    }

    @Test
    void save_cuandoClienteExiste_debeGuardarDireccionYRetornarDTO() {
        // Given
        DireccionRequestDTO requestDTO = crearDireccionRequestDTO();
        Cliente cliente = crearCliente();
        Direccion direccion = crearDireccion();
        DireccionDTO direccionDTO = crearDireccionDTO();

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(direccionMapper.toEntity(requestDTO)).thenReturn(direccion);
        when(direccionRepository.save(direccion)).thenReturn(direccion);
        when(direccionMapper.toDireccionDTO(direccion)).thenReturn(direccionDTO);

        // When
        DireccionDTO resultado = direccionService.save(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Av. Providencia", resultado.getCalle());

        verify(clienteRepository, times(1)).findById(1);
        verify(direccionMapper, times(1)).toEntity(requestDTO);
        verify(direccionRepository, times(1)).save(direccion);
        verify(direccionMapper, times(1)).toDireccionDTO(direccion);
    }

    @Test
    void save_cuandoClienteNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        DireccionRequestDTO requestDTO = crearDireccionRequestDTO();
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> direccionService.save(requestDTO)
        );

        assertEquals("Cliente no encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(1);
        verifyNoInteractions(direccionMapper);
        verify(direccionRepository, never()).save(any());
    }

    @Test
    void update_cuandoDireccionYClienteExisten_debeActualizarYRetornarDTO() {
        // Given
        DireccionRequestDTO requestDTO = crearDireccionRequestDTO();
        Direccion direccionExistente = crearDireccion();
        Cliente cliente = crearCliente();
        DireccionDTO direccionActualizadaDTO = crearDireccionDTO();

        when(direccionRepository.findById(1)).thenReturn(Optional.of(direccionExistente));
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(direccionRepository.save(direccionExistente)).thenReturn(direccionExistente);
        when(direccionMapper.toDireccionDTO(direccionExistente)).thenReturn(direccionActualizadaDTO);

        // When
        DireccionDTO resultado = direccionService.update(1, requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Av. Providencia", resultado.getCalle());

        verify(direccionRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).findById(1);
        verify(direccionRepository, times(1)).save(direccionExistente);
        verify(direccionMapper, times(1)).toDireccionDTO(direccionExistente);
    }

    @Test
    void update_cuandoDireccionNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        DireccionRequestDTO requestDTO = crearDireccionRequestDTO();
        when(direccionRepository.findById(99)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> direccionService.update(99, requestDTO)
        );

        assertEquals("Dirección no encontrada", exception.getMessage());

        verify(direccionRepository, times(1)).findById(99);
        verifyNoInteractions(clienteRepository);
        verify(direccionRepository, never()).save(any());
    }

    @Test
    void update_cuandoClienteNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        DireccionRequestDTO requestDTO = crearDireccionRequestDTO();
        Direccion direccionExistente = crearDireccion();

        when(direccionRepository.findById(1)).thenReturn(Optional.of(direccionExistente));
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> direccionService.update(1, requestDTO)
        );

        assertEquals("Cliente no encontrado", exception.getMessage());

        verify(direccionRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).findById(1);
        verify(direccionRepository, never()).save(any());
    }

    @Test
    void delete_cuandoExiste_debeEliminarCorrectamente() {
        // Given
        when(direccionRepository.existsById(1)).thenReturn(true);

        // When
        direccionService.delete(1);

        // Then
        verify(direccionRepository, times(1)).existsById(1);
        verify(direccionRepository, times(1)).deleteById(1);
    }

    @Test
    void delete_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        when(direccionRepository.existsById(99)).thenReturn(false);

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> direccionService.delete(99)
        );

        assertEquals("Dirección no encontrada", exception.getMessage());

        verify(direccionRepository, times(1)).existsById(99);
        verify(direccionRepository, never()).deleteById(anyInt());
    }

    private Cliente crearCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setRun("20456789-3");
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setEmail("juan.perez@gmail.com");
        cliente.setTelefono("+56912345678");
        cliente.setPuntosCliente(150);
        cliente.setActivo(true);
        cliente.setFechaRegistro(LocalDate.of(2026, 6, 19));
        return cliente;
    }

    private Direccion crearDireccion() {
        Direccion direccion = new Direccion();
        direccion.setId(1);
        direccion.setCalle("Av. Providencia");
        direccion.setNumero("1234");
        direccion.setComuna("Providencia");
        direccion.setCiudad("Santiago");
        direccion.setCodigoPostal(7500000);
        direccion.setPrincipal(true);
        direccion.setFechaRegistro(LocalDate.of(2026, 3, 1));
        direccion.setCliente(crearCliente());
        return direccion;
    }

    private DireccionDTO crearDireccionDTO() {
        return new DireccionDTO(
                1,
                "Av. Providencia",
                "1234",
                "Providencia",
                "Santiago",
                7500000,
                true,
                LocalDate.of(2026, 3, 1),
                1
        );
    }

    private DireccionRequestDTO crearDireccionRequestDTO() {
        return new DireccionRequestDTO(
                "Av. Providencia",
                "1234",
                "Providencia",
                "Santiago",
                7500000,
                true,
                LocalDate.of(2026, 3, 1),
                1
        );
    }

}
package com.msclientes.ms_clientes.Service;

import com.msclientes.ms_clientes.DTO.ClienteDTO;
import com.msclientes.ms_clientes.DTO.ClienteRequestDTO;
import com.msclientes.ms_clientes.Exception.ResourceNotFoundException;
import com.msclientes.ms_clientes.Mapper.ClienteMapper;
import com.msclientes.ms_clientes.Model.Cliente;
import com.msclientes.ms_clientes.Repository.ClienteRepository;
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

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void findAll_debeRetornarListaDeClientes() {
        // Given
        Cliente cliente = crearCliente();
        ClienteDTO clienteDTO = crearClienteDTO();

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        // When
        List<ClienteDTO> resultado = clienteService.findAll();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("juan.perez@gmail.com", resultado.get(0).getEmail());

        verify(clienteRepository, times(1)).findAll();
        verify(clienteMapper, times(1)).toDTO(cliente);
    }

    @Test
    void findAll_cuandoNoHayClientes_debeRetornarListaVacia() {
        // Given
        when(clienteRepository.findAll()).thenReturn(List.of());

        // When
        List<ClienteDTO> resultado = clienteService.findAll();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(clienteRepository, times(1)).findAll();
        verifyNoInteractions(clienteMapper);
    }

    @Test
    void findById_cuandoExiste_debeRetornarClienteDTO() {
        // Given
        Cliente cliente = crearCliente();
        ClienteDTO clienteDTO = crearClienteDTO();

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        // When
        ClienteDTO resultado = clienteService.findById(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("20456789-3", resultado.getRun());

        verify(clienteRepository, times(1)).findById(1);
        verify(clienteMapper, times(1)).toDTO(cliente);
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> clienteService.findById(99)
        );

        assertEquals("Cliente no encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(99);
        verifyNoInteractions(clienteMapper);
    }

    @Test
    void save_debeGuardarClienteYRetornarDTO() {
        // Given
        ClienteRequestDTO requestDTO = crearClienteRequestDTO();
        Cliente cliente = crearCliente();
        ClienteDTO clienteDTO = crearClienteDTO();

        when(clienteMapper.toEntity(requestDTO)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        // When
        ClienteDTO resultado = clienteService.save(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Pérez", resultado.getApellido());
        assertTrue(resultado.isActivo());

        verify(clienteMapper, times(1)).toEntity(requestDTO);
        verify(clienteRepository, times(1)).save(cliente);
        verify(clienteMapper, times(1)).toDTO(cliente);
    }

    @Test
    void update_cuandoExiste_debeActualizarClienteYRetornarDTO() {
        // Given
        ClienteRequestDTO requestDTO = crearClienteRequestDTO();
        Cliente clienteExistente = crearCliente();
        ClienteDTO clienteActualizadoDTO = crearClienteDTO();

        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(clienteExistente)).thenReturn(clienteExistente);
        when(clienteMapper.toDTO(clienteExistente)).thenReturn(clienteActualizadoDTO);

        // When
        ClienteDTO resultado = clienteService.update(1, requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan.perez@gmail.com", resultado.getEmail());

        verify(clienteRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).save(clienteExistente);
        verify(clienteMapper, times(1)).toDTO(clienteExistente);
    }

    @Test
    void update_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        ClienteRequestDTO requestDTO = crearClienteRequestDTO();
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> clienteService.update(99, requestDTO)
        );

        assertEquals("Cliente no encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(99);
        verify(clienteRepository, never()).save(any());
        verifyNoInteractions(clienteMapper);
    }

    @Test
    void delete_cuandoExiste_debeEliminarCorrectamente() {
        // Given
        when(clienteRepository.existsById(1)).thenReturn(true);

        // When
        clienteService.delete(1);

        // Then
        verify(clienteRepository, times(1)).existsById(1);
        verify(clienteRepository, times(1)).deleteById(1);
    }

    @Test
    void delete_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        // Given
        when(clienteRepository.existsById(99)).thenReturn(false);

        // When / Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> clienteService.delete(99)
        );

        assertEquals("Cliente no encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).existsById(99);
        verify(clienteRepository, never()).deleteById(anyInt());
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

    private ClienteDTO crearClienteDTO() {
        return new ClienteDTO(
                1,
                "20456789-3",
                "Juan",
                "Pérez",
                "juan.perez@gmail.com",
                "+56912345678",
                150,
                true,
                LocalDate.of(2026, 6, 19)
        );
    }

    private ClienteRequestDTO crearClienteRequestDTO() {
        return new ClienteRequestDTO(
                "20456789-3",
                "Juan",
                "Pérez",
                "juan.perez@gmail.com",
                "+56912345678",
                150,
                true,
                LocalDate.of(2026, 6, 19)
        );
    }

}
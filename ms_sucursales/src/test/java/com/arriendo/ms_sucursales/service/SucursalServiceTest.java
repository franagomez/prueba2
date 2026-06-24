package com.arriendo.ms_sucursales.service;

import com.arriendo.ms_sucursales.dto.SucursalRequestDTO;
import com.arriendo.ms_sucursales.dto.SucursalResponseDTO;
import com.arriendo.ms_sucursales.exception.ResourceNotFoundException;
import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.model.Sucursal;
import com.arriendo.ms_sucursales.repository.RegionRepository;
import com.arriendo.ms_sucursales.repository.SucursalRepository;
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
class SucursalServiceTest {


    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private SucursalService sucursalService;

    @Test
    void obtenerTodas_CuandoExistenSucursales_DebeRetornarLista() {

        Sucursal sucursal = new Sucursal();
        sucursal.setId(1);
        sucursal.setNombre("Sucursal Santiago");

        when(sucursalRepository.findAll()).thenReturn(List.of(sucursal));

        List<Sucursal> resultado = sucursalService.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sucursal Santiago", resultado.get(0).getNombre());

        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    void buscarOperativas_CuandoExistenOperativas_DebeRetornarLista() {

        Sucursal sucursal = new Sucursal();
        sucursal.setId(1);
        sucursal.setNombre("Sucursal Operativa");
        sucursal.setOperativa(true);

        when(sucursalRepository.buscarSucursalesOperativas())
                .thenReturn(List.of(sucursal));

        List<Sucursal> resultado = sucursalService.buscarOperativas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getOperativa());

        verify(sucursalRepository, times(1))
                .buscarSucursalesOperativas();
    }

    @Test
    void obtenerPorId_CuandoSucursalExiste_DebeRetornarSucursal() {

        Integer id = 1;

        Sucursal sucursal = new Sucursal();
        sucursal.setId(id);
        sucursal.setNombre("Sucursal Centro");

        when(sucursalRepository.findById(id))
                .thenReturn(Optional.of(sucursal));

        Sucursal resultado = sucursalService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(sucursalRepository, times(1)).findById(id);
    }

    @Test
    void obtenerPorId_CuandoSucursalNoExiste_DebeLanzarExcepcion() {

        Integer id = 99;

        when(sucursalRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> sucursalService.obtenerPorId(id)
        );
    }

    @Test
    void guardar_CuandoDatosValidos_DebeRetornarDTO() {

        SucursalRequestDTO dto = new SucursalRequestDTO();
        dto.setNombre("Sucursal Providencia");
        dto.setDireccion("Providencia 100");
        dto.setCapacidadVehiculos(40);
        dto.setOperativa(true);
        dto.setFechaApertura(LocalDate.now());
        dto.setRegionId(1);

        Region region = new Region();
        region.setId(1);

        Sucursal sucursal = new Sucursal();
        sucursal.setId(1);
        sucursal.setNombre("Sucursal Providencia");
        sucursal.setCapacidadVehiculos(40);
        sucursal.setRegion(region);

        when(regionRepository.findById(1))
                .thenReturn(Optional.of(region));

        when(sucursalRepository.save(any(Sucursal.class)))
                .thenReturn(sucursal);

        SucursalResponseDTO resultado =
                sucursalService.guardar(dto);

        assertNotNull(resultado);
        assertEquals("Sucursal Providencia",
                resultado.getNombre());

        verify(sucursalRepository, times(1))
                .save(any(Sucursal.class));
    }

    @Test
    void eliminar_CuandoSucursalExiste_DebeEliminar() {

        Integer id = 1;

        Sucursal sucursal = new Sucursal();
        sucursal.setId(id);

        when(sucursalRepository.findById(id))
                .thenReturn(Optional.of(sucursal));

        sucursalService.eliminar(id);

        verify(sucursalRepository, times(1))
                .delete(sucursal);
    }

}

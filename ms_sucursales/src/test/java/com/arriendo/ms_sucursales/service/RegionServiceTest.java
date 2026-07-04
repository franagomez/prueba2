package com.arriendo.ms_sucursales.service;

import com.arriendo.ms_sucursales.dto.RegionRequestDTO;
import com.arriendo.ms_sucursales.dto.RegionResponseDTO;
import com.arriendo.ms_sucursales.exception.ResourceNotFoundException;
import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.repository.RegionRepository;
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
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    @Test
    void obtenerTodas_CuandoExistenRegiones_DebeRetornarLista() {

        Region region = new Region();
        region.setId(1);
        region.setNombre("Metropolitana");

        when(regionRepository.findAll()).thenReturn(List.of(region));

        List<Region> resultado = regionService.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Metropolitana", resultado.get(0).getNombre());

        verify(regionRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_CuandoRegionExiste_DebeRetornarRegion() {

        Integer id = 1;

        Region region = new Region();
        region.setId(id);
        region.setNombre("Valparaíso");

        when(regionRepository.findById(id)).thenReturn(Optional.of(region));

        Region resultado = regionService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(regionRepository, times(1)).findById(id);
    }

    @Test
    void obtenerPorId_CuandoRegionNoExiste_DebeLanzarExcepcion() {

        Integer id = 99;

        when(regionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> regionService.obtenerPorId(id)
        );
    }

    @Test
    void guardar_CuandoDatosValidos_DebeRetornarDTO() {

        RegionRequestDTO dto = new RegionRequestDTO();
        dto.setNombre("Biobío");
        dto.setCodigo("BIO");
        dto.setNumeroRegion(8);
        dto.setActivo(true);
        dto.setFechaCreacion(LocalDate.now());

        Region region = new Region();
        region.setId(1);
        region.setNombre("Biobío");
        region.setCodigo("BIO");
        region.setNumeroRegion(8);
        region.setActivo(true);
        region.setFechaCreacion(dto.getFechaCreacion());

        when(regionRepository.save(any(Region.class))).thenReturn(region);

        RegionResponseDTO resultado = regionService.guardar(dto);

        assertNotNull(resultado);
        assertEquals("Biobío", resultado.getNombre());

        verify(regionRepository, times(1)).save(any(Region.class));
    }

    @Test
    void actualizar_CuandoRegionExiste_DebeActualizarYRetornar() {

        Integer id = 1;

        Region existente = new Region();
        existente.setId(id);
        existente.setNombre("Antigua");
        existente.setCodigo("ANT");
        existente.setNumeroRegion(2);
        existente.setActivo(true);
        existente.setFechaCreacion(LocalDate.of(2020, 1, 1));

        RegionRequestDTO dto = new RegionRequestDTO();
        dto.setNombre("Actualizada");
        dto.setCodigo("ACT");
        dto.setNumeroRegion(3);
        dto.setActivo(false);
        dto.setFechaCreacion(LocalDate.of(2024, 1, 1));

        when(regionRepository.findById(id)).thenReturn(Optional.of(existente));
        when(regionRepository.save(any(Region.class))).thenAnswer(inv -> inv.getArgument(0));

        Region resultado = regionService.actualizar(id, dto);

        assertNotNull(resultado);
        assertEquals("Actualizada", resultado.getNombre());
        assertEquals("ACT", resultado.getCodigo());
        assertFalse(resultado.getActivo());

        verify(regionRepository, times(1)).save(any(Region.class));
    }

    @Test
    void eliminar_CuandoRegionExiste_DebeEliminar() {

        Integer id = 1;

        Region region = new Region();
        region.setId(id);

        when(regionRepository.findById(id)).thenReturn(Optional.of(region));

        regionService.eliminar(id);

        verify(regionRepository, times(1)).delete(region);
    }
}

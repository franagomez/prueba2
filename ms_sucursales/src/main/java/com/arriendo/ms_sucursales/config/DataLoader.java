package com.arriendo.ms_sucursales.config;

import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.model.Sucursal;
import com.arriendo.ms_sucursales.repository.RegionRepository;
import com.arriendo.ms_sucursales.repository.SucursalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final SucursalRepository sucursalRepository;

    public DataLoader(
            RegionRepository regionRepository,
            SucursalRepository sucursalRepository) {

        this.regionRepository = regionRepository;
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public void run(String... args) {

        if (regionRepository.count() == 0) {

            Region region1 = regionRepository.save(
                    new Region(
                            null,
                            "Metropolitana",
                            "RM",
                            13,
                            true,
                            LocalDate.of(2025, 1, 1)
                    ));

            Region region2 = regionRepository.save(
                    new Region(
                            null,
                            "Valparaíso",
                            "VAL",
                            5,
                            true,
                            LocalDate.of(2025, 1, 1)
                    ));

            sucursalRepository.save(new Sucursal(
                    null,
                    "Sucursal Santiago Centro",
                    "Av. Alameda 123",
                    50,
                    true,
                    LocalDate.of(2020, 5, 10),
                    region1
            ));

            sucursalRepository.save(new Sucursal(
                    null,
                    "Sucursal Viña del Mar",
                    "Av. Libertad 456",
                    30,
                    true,
                    LocalDate.of(2021, 8, 15),
                    region2
            ));

            sucursalRepository.save(new Sucursal(
                    null,
                    "Sucursal Cerrada",
                    "Calle Falsa 123",
                    10,
                    false,
                    LocalDate.of(2019, 2, 1),
                    region1
            ));
        }
    }
}

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

            Region metropolitana = regionRepository.save(
                    new Region(
                            null,
                            "Metropolitana",
                            "RM",
                            13,
                            true,
                            LocalDate.of(2025, 1, 1)
                    ));

            Region valparaiso = regionRepository.save(
                    new Region(
                            null,
                            "Valparaíso",
                            "VAL",
                            5,
                            true,
                            LocalDate.of(2025, 1, 1)
                    ));

            Region biobio = regionRepository.save(
                    new Region(
                            null,
                            "Biobío",
                            "BIO",
                            8,
                            true,
                            LocalDate.of(2025, 1, 1)
                    ));

            Region araucania = regionRepository.save(
                    new Region(
                            null,
                            "La Araucanía",
                            "ARA",
                            9,
                            true,
                            LocalDate.of(2025, 1, 1)
                    ));

            Region antofagasta = regionRepository.save(
                    new Region(
                            null,
                            "Antofagasta",
                            "ANT",
                            2,
                            true,
                            LocalDate.of(2025, 1, 1)
                    ));

            Region losLagos = regionRepository.save(
                    new Region(
                            null,
                            "Los Lagos",
                            "LL",
                            10,
                            false,
                            LocalDate.of(2025, 1, 1)
                    ));

            sucursalRepository.save(new Sucursal(
                    null,
                    "Sucursal Santiago Centro",
                    "Av. Alameda 123",
                    50,
                    true,
                    LocalDate.of(2020, 5, 10),
                    metropolitana
            ));

            sucursalRepository.save(new Sucursal(
                    null,
                    "Sucursal Las Condes",
                    "Av. Apoquindo 4500",
                    35,
                    true,
                    LocalDate.of(2022, 3, 12),
                    metropolitana
            ));

            sucursalRepository.save(new Sucursal(
                    null,
                    "Sucursal Viña del Mar",
                    "Av. Libertad 456",
                    30,
                    true,
                    LocalDate.of(2021, 8, 15),
                    valparaiso
            ));

            sucursalRepository.save(new Sucursal(
                    null,
                    "Sucursal Concepción",
                    "Barros Arana 789",
                    25,
                    true,
                    LocalDate.of(2021, 11, 3),
                    biobio
            ));

            sucursalRepository.save(new Sucursal(
                    null,
                    "Sucursal Temuco",
                    "Av. Alemania 200",
                    20,
                    true,
                    LocalDate.of(2023, 2, 18),
                    araucania
            ));

            sucursalRepository.save(new Sucursal(
                    null,
                    "Sucursal Antofagasta Centro",
                    "Av. Grecia 1500",
                    18,
                    true,
                    LocalDate.of(2023, 6, 7),
                    antofagasta
            ));

            sucursalRepository.save(new Sucursal(
                    null,
                    "Sucursal Cerrada",
                    "Calle Falsa 123",
                    10,
                    false,
                    LocalDate.of(2019, 2, 1),
                    metropolitana
            ));
        }
    }
}

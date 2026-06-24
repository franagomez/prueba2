package com.msvehiculos.ms_vehiculos.Runner;

import com.msvehiculos.ms_vehiculos.Model.Categoria;
import com.msvehiculos.ms_vehiculos.Model.Vehiculo;
import com.msvehiculos.ms_vehiculos.Repository.CategoriaRepository;
import com.msvehiculos.ms_vehiculos.Repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public void run(String... args) {

        if (categoriaRepository.count() == 0 && vehiculoRepository.count() == 0) {

            Categoria categoria1 = new Categoria(null,
                    "SUV",
                    "Vehiculos deportivos utilitarios",
                    5,
                    true, LocalDate.now(),
                    null);

            Categoria categoria2 = new Categoria(null,
                    "Sedan",
                    "Vehiculos familiares",
                    10,
                    true, LocalDate.now(),
                    null);

            Categoria categoria3 = new Categoria(null,
                    "Pickup",
                    "Vehiculos de carga liviana",
                    4,
                    true, LocalDate.now(),
                    null);

            categoriaRepository.save(categoria1);
            categoriaRepository.save(categoria2);
            categoriaRepository.save(categoria3);

            Vehiculo vehiculo1 = new Vehiculo(null,
                    "AA1122",
                    "Toyota",
                    "Rav4",
                    2023,
                    45000.0,
                    true, LocalDate.now(), categoria1);

            Vehiculo vehiculo2 = new Vehiculo(null,
                    "BB3344",
                    "Hyundai",
                    "Accent",
                    2022,
                    30000.0,
                    true, LocalDate.now(), categoria2);

            Vehiculo vehiculo3 = new Vehiculo(null,
                    "CC5566",
                    "Mitsubishi",
                    "L200",
                    2021,
                    55000.0,
                    true, LocalDate.now(), categoria3);

            vehiculoRepository.save(vehiculo1);
            vehiculoRepository.save(vehiculo2);
            vehiculoRepository.save(vehiculo3);

            System.out.println("DataLoader ms-vehiculos ejecutado correctamente");
        }
    }
}

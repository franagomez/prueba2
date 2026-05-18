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
    public void run(String... args) throws Exception {

        if (categoriaRepository.count()==0){

            Categoria categoria = new Categoria();

            categoria.setNombre("SUV");
            categoria.setDescripcion("Vehiculos deportivos utilitarios");
            categoria.setCantidadVehiculos(5);
            categoria.setActiva(true);
            categoria.setFechaCreacion(LocalDate.now());

            categoriaRepository.save(categoria);

            Vehiculo vehiculo = new Vehiculo();

            vehiculo.setPatente("AA1122");
            vehiculo.setMarca("Toyota");
            vehiculo.setModelo("Rav4");
            vehiculo.setAnio(2023);
            vehiculo.setPrecioArriendoDiario(45000.0);
            vehiculo.setDisponible(true);
            vehiculo.setFechaRegistro(LocalDate.now());
            vehiculo.setCategoria(categoria);

            vehiculoRepository.save(vehiculo);

            System.out.println("Datos de prueba cargados");
        }
    }
}

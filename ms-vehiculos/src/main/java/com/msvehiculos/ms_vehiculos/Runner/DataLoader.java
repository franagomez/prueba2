package com.msvehiculos.ms_vehiculos.Runner;

import com.msvehiculos.ms_vehiculos.Model.Categoria;
import com.msvehiculos.ms_vehiculos.Model.Vehiculo;
import com.msvehiculos.ms_vehiculos.Repository.CategoriaRepository;
import com.msvehiculos.ms_vehiculos.Repository.VehiculoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final VehiculoRepository vehiculoRepository;

    public DataLoader(CategoriaRepository categoriaRepository, VehiculoRepository vehiculoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() > 0) {
            log.info("Ya existen categorías cargadas, se omite la carga inicial de datos");
            return;
        }

        log.info("Cargando datos iniciales de categorías y vehículos");

        Categoria suv = crearCategoria("SUV", "Vehículos deportivos utilitarios", 3);
        Categoria sedan = crearCategoria("Sedan", "Vehículos familiares", 2);
        Categoria pickup = crearCategoria("Pickup", "Vehículos de carga liviana", 1);
        Categoria hatchback = crearCategoria("Hatchback", "Vehículos compactos urbanos", 1);
        Categoria van = crearCategoria("Van", "Vehículos de pasajeros de alta capacidad", 0);

        categoriaRepository.saveAll(List.of(suv, sedan, pickup, hatchback, van));
        log.info("Se cargaron {} categorías de prueba", categoriaRepository.count());

        vehiculoRepository.saveAll(List.of(
                crearVehiculo("AA1122", "Toyota", "Rav4", 2023, 45000.0, true, suv),
                crearVehiculo("DD7788", "Ford", "Territory", 2022, 42000.0, true, suv),
                crearVehiculo("EE9900", "Nissan", "X-Trail", 2021, 40000.0, false, suv),
                crearVehiculo("BB3344", "Hyundai", "Accent", 2022, 30000.0, true, sedan),
                crearVehiculo("FF1234", "Toyota", "Corolla", 2023, 32000.0, true, sedan),
                crearVehiculo("CC5566", "Mitsubishi", "L200", 2021, 55000.0, true, pickup),
                crearVehiculo("GG5678", "Chevrolet", "Spark", 2022, 25000.0, true, hatchback)
        ));
        log.info("Se cargaron {} vehículos de prueba", vehiculoRepository.count());
    }

    private Categoria crearCategoria(String nombre, String descripcion, int cantidadVehiculos) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        categoria.setCantidadVehiculos(cantidadVehiculos);
        categoria.setActiva(true);
        categoria.setFechaCreacion(LocalDate.now());
        return categoria;
    }

    private Vehiculo crearVehiculo(String patente, String marca, String modelo, int anio,
                                    double precio, boolean disponible, Categoria categoria) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente(patente);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setAnio(anio);
        vehiculo.setPrecioArriendoDiario(precio);
        vehiculo.setDisponible(disponible);
        vehiculo.setFechaRegistro(LocalDate.now());
        vehiculo.setCategoria(categoria);
        return vehiculo;
    }
}

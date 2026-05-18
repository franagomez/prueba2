package com.msvehiculos.ms_vehiculos.Repository;


import com.msvehiculos.ms_vehiculos.Model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {


    // Buscamos vehiculos disponibles cuyo precio sea menor al indicado
    List<Vehiculo> findByDisponibleTrueAndPrecioArriendoDiarioLessThan(Double precio);









}

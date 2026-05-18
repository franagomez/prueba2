package com.msclientes.ms_clientes.Repository;


import com.msclientes.ms_clientes.Model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    // listamos direccion por ciudades
    List<Direccion> findByCiudad(String ciudad);

    // Buscamos direcciones principales
    List<Direccion> findByPrincipalTrue();

    // Buscamos direcciones por cliente especifico (debido a la Id)
    List<Direccion> findByClienteId(Integer clienteId);
}

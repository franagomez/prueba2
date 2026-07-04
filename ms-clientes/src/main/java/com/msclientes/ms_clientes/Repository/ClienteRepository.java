package com.msclientes.ms_clientes.Repository;


import com.msclientes.ms_clientes.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // Encuentra Clientes por el run
    List<Cliente> findByRun(String run);

    // Encuentra Clientes por el nombre y apellido
    List<Cliente> findByNombreAndApellido(String nombre, String apellido);

    // Encuentra por el email del cliente
    List<Cliente> findByEmail(String email);

    // Encuentra cliente activo
    List<Cliente> findByActivoTrue();

    // Encuentra cliente inactivo
    List<Cliente> findByActivoFalse();

    // Encuentra cliente con mayor cantidad de puntos que el máximo
    List<Cliente> findByPuntosClienteGreaterThan(int puntosCliente);

    // Encuentra clientes con puntos entre dos valores
    List<Cliente> findByPuntosClienteBetween(int puntosCliente, int puntosCliente2);

    //Encuentra clientes con puntos = 0
    List<Cliente> findByPuntosClienteEquals(int puntosCliente);

    // Busca todos los clientes cuyo email contenga un texto dado
    // sin distinguir mayúsculas ni minúsculas.
    List<Cliente> findByEmailContainingIgnoreCase(String email);

}

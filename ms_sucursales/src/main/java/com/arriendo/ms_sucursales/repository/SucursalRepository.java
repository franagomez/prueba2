package com.arriendo.ms_sucursales.repository;

import com.arriendo.ms_sucursales.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    @Query(value = """
            SELECT * 
            FROM sucursal
            WHERE operativa = true
            ORDER BY nombre ASC
            """, nativeQuery = true)
    List<Sucursal> buscarSucursalesOperativas();
}
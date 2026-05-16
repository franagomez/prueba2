package com.arriendo.ms_pagos.repository;

import com.arriendo.ms_pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query("""
            SELECT p
            FROM Pago p
            WHERE p.monto BETWEEN :minimo AND :maximo
            ORDER BY p.fechaPago DESC
            """)
    List<Pago> buscarPagosPorRango(
            @Param("minimo") Double minimo,
            @Param("maximo") Double maximo);
}

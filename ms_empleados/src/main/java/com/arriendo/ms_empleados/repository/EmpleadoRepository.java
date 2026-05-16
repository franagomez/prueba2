package com.arriendo.ms_empleados.repository;

import com.arriendo.ms_empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    @Query(value = "SELECT * FROM empleados WHERE activo = true AND YEAR(fecha_contratacion) = ?1", nativeQuery = true)
    List<Empleado> buscarActivosPorAnio(Integer anio);
}

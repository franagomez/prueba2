package com.msvehiculos.ms_vehiculos.Repository;


import com.msvehiculos.ms_vehiculos.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

}

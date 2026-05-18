package com.arriendo.ms_sucursales.repository;

import com.arriendo.ms_sucursales.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

}
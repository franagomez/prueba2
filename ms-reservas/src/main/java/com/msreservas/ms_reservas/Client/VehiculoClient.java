package com.msreservas.ms_reservas.Client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-vehiculos", url = "http://localhost:8082")
public interface VehiculoClient {

    @GetMapping("/api/v1/vehiculos/{id}")
    Object obtenerVehiculoPorId(@PathVariable Integer id);
}

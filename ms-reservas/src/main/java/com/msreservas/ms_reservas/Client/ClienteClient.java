package com.msreservas.ms_reservas.Client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clientes", url =  "http://localhost:8081")
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/{id}")
    Object obtenerClientePorId(@PathVariable Integer id);
}

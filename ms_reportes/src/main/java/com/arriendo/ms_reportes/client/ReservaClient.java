package com.arriendo.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas")
    List<Map<String, Object>> obtenerReservas();
}

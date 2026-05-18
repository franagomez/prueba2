package com.arriendo.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-pagos", url = "http://localhost:8084")
public interface PagoClient {

    @GetMapping("/api/v1/pagos")
    List<Map<String, Object>> obtenerPagos();
}

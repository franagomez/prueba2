package com.arriendo.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "ms-pagos")
public interface PagoClient {

    @GetMapping("/api/v1/pagos")
    Map<String, Object> obtenerPagos();
}
package com.msvehiculos.ms_vehiculos;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("API Microservicio Vehiculos")
                .version("v1.0")
                        .description("Documentación del microservicio encargado de gestionar vehículo y categorías" +
                                "para el sistema de arriendo de veículos."));
    }
}

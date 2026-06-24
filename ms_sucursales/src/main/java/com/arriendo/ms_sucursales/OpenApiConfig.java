package com.arriendo.ms_sucursales;

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
                    .title("API Microservicio Sucursales")
                    .version("v1.0")
                    .description("Documentación del microservicio encargado de gestionar sucursales y regiones del sistema " +
                            "de arriendo de vehículos"));
    }
}

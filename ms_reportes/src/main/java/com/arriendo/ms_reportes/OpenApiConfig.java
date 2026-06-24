package com.arriendo.ms_reportes;

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
                .title("API Microservicio Reportes")
                .version("v1.0")
                .description("Documentación del microservicio de reportes para el arriendo de vehículos"));
    }
}

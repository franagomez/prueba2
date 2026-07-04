package com.msclientes.ms_clientes;


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
                        .title("API Microservicio Clientes")
                        .version("v1.0")
                .description("Documentación técnica del microservicio de clientes y direcciones"));
    }
}

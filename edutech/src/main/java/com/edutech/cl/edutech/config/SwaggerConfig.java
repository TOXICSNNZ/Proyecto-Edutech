package com.edutech.cl.edutech.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public  OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API SISTEMA EDUTECH")
                        .version("1.0")
                        .description("Documentacion para el Sistema EDUTECH"));
    }
}
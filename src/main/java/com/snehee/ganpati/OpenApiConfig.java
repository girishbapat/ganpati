package com.snehee.ganpati;

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
                        .title("My customized API for Ganpati application")
                        .version("1.0")
                        .description("This is a custom OpenAPI configuration for my Ganpati Spring Boot application"));
    }
}

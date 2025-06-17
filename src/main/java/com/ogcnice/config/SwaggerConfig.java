package com.ogcnice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OGC Nice API")
                        .version("1.0.0")
                        .description("API de gestion des équipes de football de l'OGC Nice")
                        .contact(new Contact()
                                .name("OGC Nice")
                                .email("contact@ogcnice.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation complète")
                        .url("https://api.ogcnice.com/docs"));
    }
}

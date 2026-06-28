package com.krushisevakendra.billing.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI krushiSevaOpenAPI(){
        return  new OpenAPI()
                .info(new Info()
                        .title("Krushi Seva Kendra Billing API")
                        .description("REST API for Krushi Seva Kendra Billing Application")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Mayur Dhavale")
                                .email("mayurdhavale330@gmail.com"))
                        .license(new License()
                                .name("MD License")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation"));
    }
}

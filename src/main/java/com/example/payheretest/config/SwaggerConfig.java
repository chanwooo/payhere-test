package com.example.payheretest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "머니부기 (Money Book) API 명세서",
                description = "API 명세서",
                version = "v1",
                contact = @Contact(
                        name = "Chanwoo",
                        email = "chanwookim@me.com"
                )
        )
)
@Configuration
public class SwaggerConfig {
}

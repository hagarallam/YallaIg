package com.yallaIg.yallaIg_backend.confg;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Hagar",
                        email = "Hagar@test.com",
                        url = "https://test.ocm"
                ),
                description = "OpenApi documentation for YallaIg App",
                title = "OpenApi Documentation - YallaIg",
                version = "1.0",
                license = @License(
                       name = "License Name",
                       url = "https://test.com"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "Local Inv",
                        url = "http://localhost:8080"
                ),
                @Server(
                description = "Stg Inv",
                url = "https://ycodebase-production.up.railway.app/"
            )

        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Auth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class SwaggerConfig {

// From Ai example
//    @Bean
//    public OpenAPI springShopOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("YallaIG API")
//                        .description("YallaIG backend API documentation")
//                        .version("v1.0")
//                        .contact(new Contact()
//                                .name("YallaIG Support")
//                                .email("support@yallaig.com"))
//                        .license(new License()
//                                .name("Apache 2.0")
//                                .url("https://yallaig.com/license")));
//    }
}


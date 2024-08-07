package com.JFSEI.Storage_Management_System.Configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Storage Management System - JS SEI S.A.S",
                version = "1.0",
                description = "endpoint for application testing"
        )
)
public class OpenApiConfiguration {
}

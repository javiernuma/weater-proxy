package com.javiernuma.bootstrap.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI weatherApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Weather Proxy API")
                        .description("API that acts as a proxy to external weather services")
                        .version("1.0.0"));
    }
}

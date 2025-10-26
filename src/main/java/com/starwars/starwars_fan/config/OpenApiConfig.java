package com.starwars.starwars_fan.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI starWarsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("StarWars Fan API")
                        .description("API for displays data people and planets from a third-party " +
                                "API [SWAPI](https://swapi.dev).")
                        .version("1.0.0")
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")));
    }
}

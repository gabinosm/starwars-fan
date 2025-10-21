package com.starwars.starwars_fan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient swapiWebClient() {
        return WebClient.builder()
                .baseUrl("https://swapi.dev/api")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}

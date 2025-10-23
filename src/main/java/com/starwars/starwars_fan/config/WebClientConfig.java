package com.starwars.starwars_fan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${swapi.base-url}")
    private String swapiBaseUrl;

    @Bean
    public WebClient swapiWebClient() {
        return WebClient.builder()
                .baseUrl(swapiBaseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}

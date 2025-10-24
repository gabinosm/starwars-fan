package com.starwars.starwars_fan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(SwapiProperties.class)
public class SwapiWebClientConfig {

    @Bean
    public WebClient swapiWebClient(SwapiProperties properties, WebClient.Builder builder) {
        System.out.println("🛰️ Configurando WebClient con base URL: " + properties.getBaseUrl());
        return builder
                .baseUrl(properties.getBaseUrl())
                .defaultHeader("Accept", "application/json")
                .build();
    }
}

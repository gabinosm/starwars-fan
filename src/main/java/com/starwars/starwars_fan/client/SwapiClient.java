package com.starwars.starwars_fan.client;

import com.starwars.starwars_fan.dto.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class SwapiClient {

    private final WebClient webClient;

    public SwapiClient(WebClient swapiWebClient) {
        this.webClient = swapiWebClient;
    }

    public List<PersonDto> fetchAllPeople() {
        return fetchAllPages("people", PersonResponse.class)
                .stream()
                .flatMap(response -> response.getResults().stream())
                .toList();
    }

    public List<PlanetDto> fetchAllPlanets() {
        return fetchAllPages("planets", PlanetResponse.class)
                .stream()
                .flatMap(response -> response.getResults().stream())
                .toList();
    }

    private <T> List<T> fetchAllPages(String resource, Class<T> responseType) {
        String url = resource.startsWith("/") ? resource : "/" + resource;;
        List<T> results = new ArrayList<>();

        while (url != null) {
            T response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();

            results.add(response);
            url = extractNextPageUrl(response);
        }

        return results;
    }

    private String extractNextPageUrl(Object response) {
        try {
            var field = response.getClass().getDeclaredField("next");
            field.setAccessible(true);
            return (String) field.get(response);
        } catch (Exception e) {
            return null;
        }
    }
}

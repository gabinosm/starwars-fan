package com.starwars.starwars_fan.integration;

import com.starwars.starwars_fan.StarwarsFanApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("it")
public class StarwarsFanIT {
    @Test
    void contextLoads() {
        try {
            SpringApplication.run(StarwarsFanApplication.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

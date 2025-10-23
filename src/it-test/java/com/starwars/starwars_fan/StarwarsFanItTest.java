package com.starwars.starwars_fan;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("it")
public class StarwarsFanItTest {
    @Test
    void contextLoads() {
        try {
            SpringApplication.run(StarwarsFanApplication.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.example.tamakkun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TamkkunApplication {

    public static void main(String[] args) {
        SpringApplication.run(TamkkunApplication.class, args);
    }

}

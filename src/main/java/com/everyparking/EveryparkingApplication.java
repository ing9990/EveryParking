package com.everyparking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EveryparkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EveryparkingApplication.class, args);
    }

}

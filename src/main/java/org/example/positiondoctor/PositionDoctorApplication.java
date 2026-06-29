package org.example.positiondoctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PositionDoctorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PositionDoctorApplication.class, args);
    }

}

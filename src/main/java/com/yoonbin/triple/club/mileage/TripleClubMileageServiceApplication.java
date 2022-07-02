package com.yoonbin.triple.club.mileage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TripleClubMileageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripleClubMileageServiceApplication.class, args);
    }

}

package com.islamicbank.fa;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starting point of Flight Advisor Application.
 *
 * @author Omar Ismail
 * @version 1.0
 */
@SpringBootApplication
@Log4j2
public class FlightAdvisorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightAdvisorApiApplication.class, args);
    }

}

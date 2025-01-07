package com.islamicbank.fa;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        // Start a background task to print "Welcome to DevOps" every second
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Welcome to DevOps");
        }, 0, 1, TimeUnit.SECONDS);

        log.info("Flight Advisor Application started successfully!");
    }
    }

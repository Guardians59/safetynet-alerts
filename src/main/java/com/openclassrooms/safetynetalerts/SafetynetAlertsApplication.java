package com.openclassrooms.safetynetalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafetynetAlertsApplication {

    public static void main(String[] args) {
	SpringApplication.run(SafetynetAlertsApplication.class, args);
    }

    @Bean
    protected HttpTraceRepository httpTraceRepository() {
	return new InMemoryHttpTraceRepository();
    }

}

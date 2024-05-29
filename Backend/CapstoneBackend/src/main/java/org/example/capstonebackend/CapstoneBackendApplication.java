package org.example.capstonebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class CapstoneBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapstoneBackendApplication.class, args);
    }

}

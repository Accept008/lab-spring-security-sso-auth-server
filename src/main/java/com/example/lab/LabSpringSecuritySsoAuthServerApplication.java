package com.example.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication
@ComponentScan({"com.example"})
public class LabSpringSecuritySsoAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabSpringSecuritySsoAuthServerApplication.class, args);
    }

}

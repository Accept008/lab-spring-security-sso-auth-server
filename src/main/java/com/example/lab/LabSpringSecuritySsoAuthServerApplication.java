package com.example.lab;

import cn.soundbus.library.spring.boot.autoconfigure.oauth2.OAuth2AutoConfiguration;
import cn.soundbus.spring.swagger.boot.autoconfigure.EnableSwagger2Doc;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


//@SpringBootApplication
//@EnableResourceServer

@SpringBootApplication(exclude = {OAuth2AutoConfiguration.class})
@EnableResourceServer
@ComponentScan(value = "cn.soundbus.library.spring.boot.autoconfigure.oauth2.resourceserver", excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableResourceServer.class) })
@ComponentScan(value = "cn.soundbus.library.spring.boot.autoconfigure.oauth2.token", excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Aspect.class) })

public class LabSpringSecuritySsoAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabSpringSecuritySsoAuthServerApplication.class, args);
    }

}

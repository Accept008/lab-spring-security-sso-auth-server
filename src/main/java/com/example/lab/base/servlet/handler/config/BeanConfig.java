package com.example.lab.base.servlet.handler.config;

import com.example.lab.base.servlet.handler.DefaultRestExceptionResolver;
import com.example.lab.base.servlet.handler.RestExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public RestExceptionResolver getDefaultRestExceptionResolver(){
        return new DefaultRestExceptionResolver();
    }
}

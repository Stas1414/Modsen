package com.example.mainservice.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateCfg {
    @Bean
    public RestTemplate newTemplate(){
        return new RestTemplate();
    }
}

package com.bz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CartMain{

    public static void main(String[] args) {
        SpringApplication.run(CartMain.class,args);
    }

    @Bean
    public RestTemplate getRestTeplate(){
        return new RestTemplate();
    }
}

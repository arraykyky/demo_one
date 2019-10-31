package com.springcloud.book.emailsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailSystemApplication {
    public static void main(String[] args){
        SpringApplication.run(EmailSystemApplication.class,args);
    }
}

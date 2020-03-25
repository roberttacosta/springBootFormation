package com.formacao.demo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients(basePackages = {"com.formacao.demo.integration"})
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.formacao.demo.repository")
@EntityScan(basePackages = "com.formacao.demo.domain")
@ComponentScan(basePackages = {"com.formacao.demo"}, excludeFilters = @ComponentScan.Filter(FeignClient.class))

public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

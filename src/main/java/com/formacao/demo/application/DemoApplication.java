package com.formacao.demo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.formacao.demo.domain",
								"com.formacao.demo.controller",
								"com.formacao.demo.dto",
								"com.formacao.demo.repository",
								"com.formacao.demo.service",
								"com.formacao.demo.application"})
@Configuration
public class DemoApplication {

//	public DemoApplication() throws ParseException {
//	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	}

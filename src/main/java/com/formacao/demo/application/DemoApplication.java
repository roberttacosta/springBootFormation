package com.formacao.demo.application;

import com.formacao.demo.integration.configuration.OMDBApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableFeignClients("com.formacao.demo.integration")
@SpringBootApplication
@EnableJpaRepositories("com.formacao.demo.repository")
@EntityScan ("com.formacao.demo.domain")
public class DemoApplication {

	public static OMDBApi omdbApi;

	@Autowired
	public DemoApplication(OMDBApi omdbApi) {
		this.omdbApi = omdbApi;
	}

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

		omdbApi.findById("tt1285010", "a549d02f");
	}




	}
package com.formacao.demo.application;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.enums.Profile;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.ClientRepository;
import com.formacao.demo.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.Arrays;

@EnableFeignClients(basePackages = {"com.formacao.demo.integration"})
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.formacao.demo.repository")
@EntityScan(basePackages = "com.formacao.demo.domain")
@ComponentScan(basePackages = {"com.formacao.demo"}, excludeFilters = @ComponentScan.Filter(FeignClient.class))

public class DemoApplication implements CommandLineRunner {

    private ClientRepository clientRepository;
    private AccountRepository accountRepository;

    public DemoApplication(ClientRepository clientRepository, AccountRepository accountRepository, ClientService clientService){
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if(clientRepository.findByCpf("50729654060") == null){
        Account account = new Account(null, LocalDateTime.now(), 200.00);
        accountRepository.saveAll(Arrays.asList(account));

        Client client = new Client(null, "Admin", "50729654060", "admin@admin.com", "123456", account);
        client.addProfile(Profile.ROLE_ADMIN);
        clientRepository.saveAll(Arrays.asList(client));}
    }
}

package com.formacao.demo;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.domain.enums.TypeTransaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.ClientRepository;
import com.formacao.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

	public DemoApplication() throws ParseException {
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	}

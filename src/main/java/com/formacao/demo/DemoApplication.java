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
public class DemoApplication implements CommandLineRunner {

	public DemoApplication() throws ParseException {
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Account ac1 = new Account(null, LocalDateTime.now(), 200.00);
		Account ac2 = new Account(null, LocalDateTime.now(),400.00);

		accountRepository.saveAll(Arrays.asList(ac1,ac2));

		Client c1 = new Client(null, "Josildo Teste", "12659459690", ac1);
		Client c2 = new Client(null, "Joseph Teste", "67611680668",  ac2);

		clientRepository.saveAll(Arrays.asList(c1,c2));

		Transaction t1 = new Transaction(null, ac1, null, 200.00, sdf.parse("19/02/2020"), TypeTransaction.DEPOSIT );
		Transaction t2 = new Transaction(null, ac1, null, 50.00, sdf.parse("19/02/2020"), TypeTransaction.WITHDRAW );

		transactionRepository.saveAll(Arrays.asList(t1,t2));

	}
}

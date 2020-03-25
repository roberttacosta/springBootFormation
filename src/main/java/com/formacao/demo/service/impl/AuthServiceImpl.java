package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Client;
import com.formacao.demo.service.AuthService;
import com.formacao.demo.service.ClientService;
import com.formacao.demo.service.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    private ClientService clientService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailService emailService;

    private AuthServiceImpl(ClientService clientService, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.clientService = clientService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    private Random random = new Random();

    @Override
    public void sendNewPassword(String email) {

        Client client = clientService.findByEmail(email);

        String newPassword = newPassword();
        client.setPassword(bCryptPasswordEncoder.encode(newPassword));

        clientService.update(client);
        emailService.sendNewPasswordEmail(client, newPassword);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        if (opt == 0) { // gera um digito
            return (char) (random.nextInt(10) + 48);
        } else if (opt == 1) { // gera letra maiuscula
            return (char) (random.nextInt(26) + 65);
        } else { // gera letra minuscula
            return (char) (random.nextInt(26) + 97);
        }
    }
}

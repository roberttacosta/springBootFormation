package com.formacao.demo.service;

import com.formacao.demo.domain.Client;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(SimpleMailMessage simpleMailMessage);

    void sendNewPasswordEmail(Client client, String newPass);
}

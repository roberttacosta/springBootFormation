package com.formacao.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl extends EmailServiceAbstract {

    private MailSender mailSender;

    public EmailServiceImpl(MailSender mailSender){
        this.mailSender = mailSender;
    }

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        logger.info("Sending email...");
        mailSender.send(simpleMailMessage);
        logger.info("Sent email");
    }
}

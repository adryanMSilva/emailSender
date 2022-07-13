package com.example.emailsender.services;

import com.example.emailsender.controllers.handler.EmailException;
import com.example.emailsender.entities.EmailSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(final EmailSubject to) {
        final var mimeMessage = mailSender.createMimeMessage();
        final var helper = new MimeMessageHelper(mimeMessage, "utf-8");
        final var html = "<div style=\"text-align: center;\">\n" +
                "            <h1 style=\"font-size: 36px;\">Olá " + to.getName() + "</h1>\n" +
                "            <h1 style=\"font-size: 30px;\">Falta pouco para finalizar seu cadastro</h1>\n" +
                "            <h1 style=\"font-weight: normal;\">Seu token é:</h1>\n" +
                "            <h1><span style=\"color: white; background-color: mediumorchid; font-size: 58px; font-weight: bold;\">" + to.getToken() + "</span></h1>\n" +
                "        </div>";

        try {
            helper.setText(html, true);
            helper.setTo(to.getEmail());
            helper.setSubject("Finalizar cadastro");
            helper.setFrom("emailqueeucrieiprausarspring@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException("Verifique o email e tente novamente em alguns minutos");
        }
    }
}

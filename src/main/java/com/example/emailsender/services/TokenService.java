package com.example.emailsender.services;

import com.example.emailsender.entities.EmailReceiver;
import com.example.emailsender.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class TokenService {

    @Autowired
    private TokenRepository repository;

    public String insertToken(final String userEmail){
        final var existentToken = repository.findByEmail(userEmail);
        if(Objects.nonNull(existentToken)){
            repository.delete(existentToken);
        }
        final var token = EmailReceiver.builder()
                .email(userEmail)
                .token(generate())
                .build();

        repository.insert(token);

        return token.getToken();
    }

    public boolean validateToken(final EmailReceiver emailReceiverSrc) {

        final var tokenFnd = repository.findByEmailAndToken(emailReceiverSrc.getEmail(), emailReceiverSrc.getToken());

        if(Objects.nonNull(tokenFnd)){
            repository.delete(tokenFnd);
            return true;
        }

        return false;
    }


    private String generate() {
        boolean valid = false;
        String generatedToken;
        final var rd = new Random();

        do {
            generatedToken = String.valueOf(rd.nextInt(999999));

            if(Objects.isNull(repository.findByToken(generatedToken))) {
                valid = true;
            }
        } while (!valid);

        return generatedToken;
    }
}
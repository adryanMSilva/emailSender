package com.example.emailsender.services;

import com.example.emailsender.controllers.handler.ExpiredTokenException;
import com.example.emailsender.controllers.handler.ExistingTokenException;
import com.example.emailsender.controllers.handler.TokenNotFoundException;
import com.example.emailsender.entities.Token;
import com.example.emailsender.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Random;

@Service
public class TokenService {

    @Autowired
    private TokenRepository repository;

    public String insertToken(final String userEmail){
        final var existentToken = repository.findByEmail(userEmail);
        if(Objects.nonNull(existentToken)){
            if(Instant.now().getEpochSecond() - existentToken.getDate().getEpochSecond() > 3600) {
                repository.delete(existentToken);
            } else {
                LocalTime d = existentToken.getDate().plusSeconds(3600)
                        .atZone(ZoneId.of("America/Sao_Paulo")).toLocalTime();
                throw new ExistingTokenException("Seu token expira as: " + d.toString(), existentToken.getEmail());
            }
        }
        final var token = Token.builder()
                .email(userEmail)
                .token(generate())
                .date(Instant.now())
                .build();

        repository.insert(token);

        return token.getToken();
    }

    public void validateToken(final Token tokenSrc) {

        final var tokenFnd = repository.findByEmailAndToken(tokenSrc.getEmail(), tokenSrc.getToken());

        if(Objects.nonNull(tokenFnd)){
            if(Instant.now().getEpochSecond() - tokenFnd.getDate().getEpochSecond() < 3600) {
                repository.delete(tokenFnd);
            } else {
                throw new ExpiredTokenException("Por favor, gere um novo token");
            }
        } else {
            throw new TokenNotFoundException("Não foi encontrado o token " + tokenSrc.getToken()
                    + " associado com o email " + tokenSrc.getEmail());
        }
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
package com.example.emailsender.services;

import com.example.emailsender.EmailSenderApplication;
import com.example.emailsender.controllers.handler.exceptions.ExpiredTokenException;
import com.example.emailsender.controllers.handler.exceptions.ExistingTokenException;
import com.example.emailsender.controllers.handler.exceptions.TokenNotFoundException;
import com.example.emailsender.entities.Token;
import com.example.emailsender.repositories.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    private TokenRepository repository;

    private Validator validator;

    private static Logger logger = LoggerFactory.getLogger(EmailSenderApplication.class);

    public String insertToken(final String userEmail){
        final var existentToken = repository.findByEmail(userEmail);
        if(Objects.nonNull(existentToken)){
            if(isExpired(existentToken)) {
                logger.info("An expired token was found, deleting it...");
                repository.delete(existentToken);
            } else {
                logger.error("A valid token was found");
                LocalTime d = existentToken.getDate().plusSeconds(3600)
                        .atZone(ZoneId.of("America/Sao_Paulo")).toLocalTime();
                throw new ExistingTokenException("Seu token expira às: " + d.toString(), existentToken.getEmail());
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
        createValidator();
        Set<ConstraintViolation<Token>> violations = validator.validate(tokenSrc);

        if(!violations.isEmpty()){
            logger.error("Constraint violations founded");
            throw new ConstraintViolationException(violations);
        }

        final var tokenFnd = repository.findByEmailAndToken(tokenSrc.getEmail(), tokenSrc.getToken());

        if(Objects.nonNull(tokenFnd)){
            if(!isExpired(tokenFnd)) {
                logger.info("Token found, deleting it...");
                repository.delete(tokenFnd);
            } else {
                logger.error("The token {} is expired", tokenFnd.getToken());
                throw new ExpiredTokenException("Por favor, gere um novo token");
            }
        } else {
            logger.error("Token {} not found",tokenSrc.getToken());
            throw new TokenNotFoundException("Não foi encontrado o token " + tokenSrc.getToken()
                    + " associado com o email " + tokenSrc.getEmail());
        }
    }


    public void clearExpiredTokens(){
        var tokens = repository.findAll();

        logger.info("Removing the expired tokens");

        tokens = tokens.stream()
                        .filter(this::isExpired)
                                .collect(Collectors.toList());

        repository.deleteAll(tokens);
    }


    private String generate() {
        boolean valid = false;
        String generatedToken;
        final var rd = new Random();

        do {
            generatedToken = String.format("%06d",rd.nextInt(999999));

            if(Objects.isNull(repository.findByToken(generatedToken))) {
                valid = true;
            }
        } while (!valid);

        return generatedToken;
    }

    private void createValidator(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private boolean isExpired(final Token token){
        if(Instant.now().getEpochSecond() - token.getDate().getEpochSecond() < 3600) {
            return false;
        }
        return true;
    }
}
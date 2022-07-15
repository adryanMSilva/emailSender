package com.example.emailsender.controllers.handler.exceptions;

public class TokenNotFoundException extends RuntimeException{

    public TokenNotFoundException(String message){
        super(message);
    }
}

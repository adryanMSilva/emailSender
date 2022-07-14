package com.example.emailsender.controllers.handler;

public class TokenNotFoundException extends RuntimeException{

    public TokenNotFoundException(String message){
        super(message);
    }
}

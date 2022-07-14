package com.example.emailsender.controllers.handler;

import lombok.Getter;

@Getter
public class ExistingTokenException extends RuntimeException{
    private String email;
    public ExistingTokenException(String message, String email){
        super(message);
        this.email = email;
    }
}

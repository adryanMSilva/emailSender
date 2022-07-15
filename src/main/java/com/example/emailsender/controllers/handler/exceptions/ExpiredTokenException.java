package com.example.emailsender.controllers.handler.exceptions;

public class ExpiredTokenException extends RuntimeException{

    public ExpiredTokenException(String message){
        super(message);
    }
}

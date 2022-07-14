package com.example.emailsender.controllers.handler;

public class ExpiredTokenException extends RuntimeException{

    public ExpiredTokenException(String message){
        super(message);
    }
}

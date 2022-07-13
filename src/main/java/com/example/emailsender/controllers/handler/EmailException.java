package com.example.emailsender.controllers.handler;

public class EmailException extends RuntimeException{
    public EmailException(String message){
        super(message);
    }
}
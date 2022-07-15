package com.example.emailsender.controllers.handler.exceptions;

public class EmailException extends RuntimeException{
    public EmailException(String message){
        super(message);
    }
}
package com.example.emailsender.controllers.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RestControllerAdvice
public class EmailExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmailException.class)
    public ResponseEntity<StandardError> errorSendingEmail(EmailException e, HttpServletRequest request){
        StandardError er = StandardError.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error("Erro ao enviar o email")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(er);
    }

}

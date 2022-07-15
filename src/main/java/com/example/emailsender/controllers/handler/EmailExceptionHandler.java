package com.example.emailsender.controllers.handler;

import com.example.emailsender.controllers.handler.errors.ObjectError;
import com.example.emailsender.controllers.handler.errors.RequestError;
import com.example.emailsender.controllers.handler.errors.StandardError;
import com.example.emailsender.controllers.handler.exceptions.EmailException;
import com.example.emailsender.controllers.handler.exceptions.ExistingTokenException;
import com.example.emailsender.controllers.handler.exceptions.ExpiredTokenException;
import com.example.emailsender.controllers.handler.exceptions.TokenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ExceptionHandler(ExistingTokenException.class)
    public ResponseEntity<StandardError> errorValidTokenAlreadyExists(ExistingTokenException e, HttpServletRequest request){
        StandardError er = StandardError.builder()
                .status(HttpStatus.CONFLICT.value())
                .error("Já existe um token válido gerado para o email " + e.getEmail())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(er);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<StandardError> errorExpiredToken(ExpiredTokenException e, HttpServletRequest request){
        StandardError er = StandardError.builder()
                .status(HttpStatus.PRECONDITION_FAILED.value())
                .error("O token informado já está expirado")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(er);
    }


    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<StandardError> errorTokenNotFound(TokenNotFoundException e, HttpServletRequest request){
        StandardError er = StandardError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("Token não encontrado")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RequestError> errorConstraintViolated(ConstraintViolationException e, HttpServletRequest request){
        RequestError er = RequestError.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errors(obterErrosConstraints(e.getConstraintViolations()))
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }


    private List<ObjectError> obterErrosConstraints(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream().
                map(error -> ObjectError.builder()
                        .message(error.getMessage())
                        .field(error.getPropertyPath().toString())
                        .value(error.getInvalidValue())
                        .build())
                .collect(Collectors.toList());
    }
}

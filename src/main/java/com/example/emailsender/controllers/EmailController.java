package com.example.emailsender.controllers;

import com.example.emailsender.entities.DTO.EmailSubjectDTO;
import com.example.emailsender.entities.DTO.TokenDTO;
import com.example.emailsender.entities.EmailSubject;
import com.example.emailsender.entities.Token;
import com.example.emailsender.services.EmailService;
import com.example.emailsender.services.TokenService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Email enviado com sucesso"),
            @ApiResponse(code = 400, message = "Erro nas informações recebidas do usuário"),
            @ApiResponse(code = 409, message = "Já existe um token válido associado ao email"),
            @ApiResponse(code = 422, message = "Erro ao enviar o email"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping(value = "/send", produces = "application/JSON;charset=UTF-8")
    public ResponseEntity<Void> send(@Valid @RequestBody final EmailSubjectDTO receiver){
        final var convertedReceiver = EmailSubject.builder()
                .name(receiver.getName())
                .email(receiver.getEmail())
                .build();
        emailService.sendMail(convertedReceiver);
        return ResponseEntity.noContent().build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Token validado com sucesso"),
            @ApiResponse(code = 400, message = "Erro nas informações recebidas do usuário"),
            @ApiResponse(code = 404, message = "Token não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping(value = "/validate")
    public ResponseEntity<Void> validate(@Valid @RequestBody final TokenDTO token){
        final var convertedToken = Token.builder()
                .email(token.getEmail())
                .token(token.getToken())
                .build();
        tokenService.validateToken(convertedToken);
        return ResponseEntity.noContent().build();
    }
}

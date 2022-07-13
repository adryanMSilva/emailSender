package com.example.emailsender.controllers;

import com.example.emailsender.entities.EmailSubject;
import com.example.emailsender.entities.EmailReceiver;
import com.example.emailsender.services.EmailService;
import com.example.emailsender.services.TokenService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Email enviado com sucesso"),
            @ApiResponse(code = 422, message = "Erro ao enviar o email"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping(value = "/send")
    public ResponseEntity<Void> send(@RequestBody final EmailSubject receiver){
        receiver.setToken(tokenService.insertToken(receiver.getEmail()));
        emailService.sendMail(receiver);
        return ResponseEntity.noContent().build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Serviço executado"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/validate")
    public ResponseEntity<Boolean> validate(@RequestBody final EmailReceiver emailReceiver){
        return ResponseEntity.ok().body(tokenService.validateToken(emailReceiver));
    }
}

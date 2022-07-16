package com.example.emailsender.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Document
public class Token {
    @Id
    private String id;

    @NotNull(message = "O campo token é obrigatório")
    @Size(max = 6, min = 6, message = "O token precisa ter 6 caracteres")
    @Pattern(regexp = "^[0-9]*$", message = "O token precisa conter apenas numeros")
    private String token;

    @NotNull(message = "O campo email é obrigatório")
    @Pattern(regexp = "^[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}$",
            message = "Email inválido")
    private String email;

    private Instant date;
}

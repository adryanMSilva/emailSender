package com.example.emailsender.entities;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class EmailSubject {
    @NotNull(message = "O campo name é obrigatório")
    @Size(min = 5, max = 75, message = "O campo nome deve ter de 5 a 75 caracteres")
    private String name;

    @NotNull(message = "O campo email é obrigatório")
    @Pattern(regexp = "^[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}$",
            message = "Email inválido")
    private String email;

    private String token;
}

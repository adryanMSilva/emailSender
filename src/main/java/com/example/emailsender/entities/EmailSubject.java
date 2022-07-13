package com.example.emailsender.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class EmailSubject {
    private String name;
    private String email;
    private String token;
}

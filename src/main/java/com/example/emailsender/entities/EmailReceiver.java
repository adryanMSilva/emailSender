package com.example.emailsender.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Document
public class EmailReceiver {
    @Id
    private String id;
    private String token;
    private String email;
}

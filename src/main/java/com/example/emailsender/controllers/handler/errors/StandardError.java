package com.example.emailsender.controllers.handler.errors;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class StandardError {
    private Integer status;
    private String error;
    private String message;
    private String path;
}

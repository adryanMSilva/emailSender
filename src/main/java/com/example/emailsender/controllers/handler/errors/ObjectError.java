package com.example.emailsender.controllers.handler.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ObjectError {
    private String message;
    private String field;
    private Object value;
}

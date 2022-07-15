package com.example.emailsender.controllers.handler.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class RequestError {
    private Integer statusCode;
    private List<ObjectError> errors;
    private String path;
}
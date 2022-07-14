package com.example.emailsender.entities.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TokenDTO {
    @ApiModelProperty(required = true)
    private String token;
    @ApiModelProperty(required = true)
    private String email;
}

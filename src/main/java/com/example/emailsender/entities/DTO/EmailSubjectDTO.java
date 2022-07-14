package com.example.emailsender.entities.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailSubjectDTO {
    @ApiModelProperty(required = true)
    private String name;
    @ApiModelProperty(required = true)
    private String email;
}

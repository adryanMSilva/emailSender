package com.example.emailsender.entities;

import com.example.emailsender.util.AutomationException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SendEmailEntity {
    private String name;
    private String email;

    public SendEmailEntity(String dados){
        final var list = List.of(dados.split(","));

        for (int i = 0; i < list.size(); i++){
            var fields = this.getClass().getDeclaredFields();
            try {
                if(list.get(i).equals("null")){
                    fields[i].set(this,null);
                } else {
                    fields[i].set(this, list.get(i));
                }
            } catch (IllegalAccessException e) {
                throw new AutomationException(e.getMessage());
            }
        }


    }
}

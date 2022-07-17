package com.example.emailsender.request;

import com.example.emailsender.entities.SendEmailEntity;
import com.example.emailsender.util.AutomationException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class SendEmailRequestGenerator {
    private SendEmailEntity entity;

    private JSONObject req = new JSONObject();

    public SendEmailRequestGenerator(SendEmailEntity entity) {
        this.entity = entity;
    }

    public JSONObject generate(){
        try {
            name();
            email();
        } catch (Exception e){
            throw new AutomationException(Arrays.toString(e.getStackTrace()));
        }
        return req;
    }

    private void name() throws JSONException {
        if(Objects.nonNull(entity.getName())) {
            req.put("name", entity.getName());
        }
    }

    private void email() throws JSONException{
        if(Objects.nonNull(entity.getEmail())) {
            if (entity.getEmail().equalsIgnoreCase("GENERATE")) {
                req.put("email", createRandomEmail());
            } else {
                req.put("email", entity.getEmail());
            }
        }
    }

    private String createRandomEmail() {
        var SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        var salt = new StringBuilder();
        var rnd = new Random();
        while (salt.length() < 10) {
            var index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        return salt.toString().concat("@gmail.com");
    }
}

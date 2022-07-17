package com.example.emailsender.steps;

import com.example.emailsender.entities.SendEmailEntity;
import com.example.emailsender.request.SendEmailRequestGenerator;
import com.example.emailsender.rest.RestConsumer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SendEmail extends RestConsumer {
    private SendEmailEntity entity;
    private SendEmailRequestGenerator request;
    @Given("informe o request {string}")
    public void informeORequest(String infos) {
        entity = new SendEmailEntity(infos);
        request = new SendEmailRequestGenerator(entity);
    }
    @When("tentar enviar o email")
    public void tentarEnviarOEmail() {
        post("email/send",request.generate());
    }
    @Then("ocorre um erro {int}")
    public void ocorreUmErro(Integer expected) {
        validateStatusCode(expected);
    }
}

package com.example.emailsender.rest;

import com.example.emailsender.EmailSenderApplication;
import com.example.emailsender.util.AutomationException;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class RestConsumer {
    private final String url = "https://api-email-spring.herokuapp.com/";
    private HttpResponse<?> httpResponse;

    private static Logger logger = LoggerFactory.getLogger(EmailSenderApplication.class);

    public void post(final String endpoint, final JSONObject requestBody){
        try {
            final var client = HttpClient.newHttpClient();
            final var request = HttpRequest.newBuilder(
                    URI.create(url + endpoint))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .setHeader("Content-type","application/json")
                    .setHeader("Accept","application/json")
                    .build();

            logger.info("Sending request \n{}\n to the url {}", requestBody.toString(4), (url+endpoint));
            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(!response.body().isEmpty()) {
                logger.info("Response: \n{}", new JSONObject(response.body()).toString(4).replaceAll("\\\\",""));
            } else {
                logger.info("No response body");
            }
            httpResponse = response;
        } catch (Exception e){
            throw new AutomationException(Arrays.toString(e.getStackTrace()));
        }

    }

    public void validateStatusCode(int expected){
        logger.info("Status code -> Expected: {} \t|\tActual: {}\n\n", expected, httpResponse.statusCode());
        Assert.assertEquals(expected,httpResponse.statusCode());
    }
}

package com.example.emailsender.schedulers;

import com.example.emailsender.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TokenScheduler {

    @Autowired
    private TokenService tokenService;

    @Scheduled(cron = "0 0 5 * * ?", zone = "America/Sao_Paulo")
    public void clearExpiredTokens(){
        tokenService.clearExpiredTokens();
    }
}

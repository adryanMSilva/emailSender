package com.example.emailsender.repositories;

import com.example.emailsender.entities.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TokenRepository extends MongoRepository<Token,String> {

    @Query("{'email': ?0, 'token': ?1}")
    Token findByEmailAndToken(final String email, final String token);

    @Query("{'email': ?0}")
    Token findByEmail(final String email);

    @Query("{'token': ?0}")
    Token findByToken(final String token);
}

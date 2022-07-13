package com.example.emailsender.repositories;

import com.example.emailsender.entities.EmailReceiver;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TokenRepository extends MongoRepository<EmailReceiver,String> {

    @Query("{'email': ?0, 'token': ?1}")
    EmailReceiver findByEmailAndToken(final String email, final String token);

    @Query("{'email': ?0}")
    EmailReceiver findByEmail(final String email);

    @Query("{'token': ?0}")
    EmailReceiver findByToken(final String token);
}

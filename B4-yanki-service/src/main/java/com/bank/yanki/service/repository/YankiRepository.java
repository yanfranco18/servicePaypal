package com.bank.yanki.service.repository;

import com.bank.yanki.service.model.Yanki;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface YankiRepository extends ReactiveMongoRepository<Yanki, String> {

    Mono<Yanki> findByPhoneNumber(String phoneNumber);

}
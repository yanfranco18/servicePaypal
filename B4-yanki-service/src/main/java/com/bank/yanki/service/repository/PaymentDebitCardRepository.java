package com.bank.yanki.service.repository;

import com.bank.yanki.service.model.PaymentDebitCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PaymentDebitCardRepository extends ReactiveMongoRepository<PaymentDebitCard, String> {

    Mono<PaymentDebitCard> findByPhoneNumber(String phoneNumber);
}
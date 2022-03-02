package com.bank.yanki.service.service;

import com.bank.yanki.service.model.Debit;
import com.bank.yanki.service.model.PaymentDebitCard;
import com.bank.yanki.service.repository.PaymentDebitCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class PaymentDebitCardService implements IPaymentDebitCardService{

    @Autowired
    private PaymentDebitCardRepository repository;
    @Autowired
    private ObjectMapper objectMapper;
    private final WebClient webClient;
    String uri = "http://localhost:8097/debits";

    public PaymentDebitCardService() {
        this.webClient = WebClient.builder().baseUrl(this.uri).build();
    }

    @Override
    public Mono<PaymentDebitCard> save(PaymentDebitCard paymentDebitCard) {
        return repository.save(paymentDebitCard).flatMap(this::getDebitAccount);
    }

    @Override
    public Mono<PaymentDebitCard> getDebitAccount(PaymentDebitCard paymentDebitCard) {
        return Mono.just(objectMapper.convertValue(paymentDebitCard, PaymentDebitCard.class));
    }

    @Override
    public Mono<PaymentDebitCard> findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).flatMap(this::getPaymentDebitCard);
    }

    @Override
    public Mono<Debit> getDebitById(String idDebitCardNumber) {
        return webClient.get().uri(this.uri + "/getById/{idDebitCardNumber}", idDebitCardNumber)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Debit.class);
    }

    @Override
    public Mono<PaymentDebitCard> getPaymentDebitCard(PaymentDebitCard paymentDebitCard) {
        return Mono.just(objectMapper.convertValue(paymentDebitCard, PaymentDebitCard.class));
    }
}

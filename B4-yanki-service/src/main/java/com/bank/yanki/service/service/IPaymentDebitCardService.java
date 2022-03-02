package com.bank.yanki.service.service;

import com.bank.yanki.service.model.Debit;
import com.bank.yanki.service.model.PaymentDebitCard;
import reactor.core.publisher.Mono;

public interface IPaymentDebitCardService
{
    Mono<PaymentDebitCard> save(PaymentDebitCard paymentDebitCard);
    Mono<PaymentDebitCard> getDebitAccount(PaymentDebitCard paymentDebitCard);
    Mono<PaymentDebitCard> findByPhoneNumber(String phoneNumber);
    Mono<Debit> getDebitById(String idDebitCardNumber);
    Mono<PaymentDebitCard> getPaymentDebitCard(PaymentDebitCard paymentDebitCard);
}

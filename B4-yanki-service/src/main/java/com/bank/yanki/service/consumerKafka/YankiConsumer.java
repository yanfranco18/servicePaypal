package com.bank.yanki.service.consumerKafka;


import com.bank.yanki.service.model.dto.YankiTransactionDto;
import com.bank.yanki.service.producerKafka.YankiProducer;
import com.bank.yanki.service.service.IYankiService;
import com.bank.yanki.service.service.PaymentDebitCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class YankiConsumer {
    @Autowired
    private IYankiService service;

    @Autowired
    private PaymentDebitCardService paymentDebitCardService;

    @Autowired
    private YankiProducer producer;

    @KafkaListener(
            topics = "${custom.kafka.topic-name}",
            groupId = "${custom.kafka.group-id}",
            containerFactory = "yankiKafkaListenerContainerFactory")
    public void consumer(YankiTransactionDto yankiTransactionDto) {

        log.info("Mensaje consumido [{}]", yankiTransactionDto);
        this.validarBalance(yankiTransactionDto);
    }

    private void validarBalance(YankiTransactionDto yankiTransactionDto) {

        service.findByPhoneNumber(yankiTransactionDto.getOriginNumber())
                .flatMap(m -> {

                    if (m.getAmount() >= yankiTransactionDto.getAmount()) {

                        Double newAmount = m.getAmount() - yankiTransactionDto.getAmount();
                        m.setAmount(newAmount);
                        service.getYankiAll(m).flatMap(mObj -> service.save(mObj))
                                .flatMap(d -> service.findByPhoneNumber(yankiTransactionDto.getDestinationNumber()))
                                .flatMap(md -> service.getYankiAll(md).flatMap(mde -> {
                                    mde.setAmount(mde.getAmount() + yankiTransactionDto.getAmount());
                                    return service.save(mde);
                                })).subscribe(c -> log.info("UPDATE ALL GA"));

                        yankiTransactionDto.setStatus(YankiTransactionDto.Status.SUCCESSFUL);

                    } else {

                        //REVISAR SI TIENE DEBITO ASOCIADA
                        paymentDebitCardService.findByPhoneNumber(yankiTransactionDto.getOriginNumber()).flatMap(mdc -> {
                                    log.info("getId: "+ mdc.getId());
                                    return paymentDebitCardService.getPaymentDebitCard(mdc);
                                }
                        );

                        // SI NO TIENE -> RECHAZAR PETICION
                        yankiTransactionDto.setStatus(YankiTransactionDto.Status.REJECTED);
                    }
                    this.producer.producer(yankiTransactionDto);
                    return service.getYankiAll(m);
                }).subscribe();
    }




}

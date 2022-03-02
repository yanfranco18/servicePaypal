package com.bank.yanki.service.producerKafka;

import com.bank.yanki.service.model.dto.YankiTransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class YankiProducer {
    @Value("${custom.kafka.topic-name-yanki}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, YankiTransactionDto> transactionDtoKafkaTemplate;

    public void producer(YankiTransactionDto transactionDto) {
        transactionDtoKafkaTemplate.send(topicName, transactionDto);
    }
}

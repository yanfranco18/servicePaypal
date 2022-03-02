package com.bank.yanki.service.service;

import com.bank.yanki.service.model.Yanki;
import com.bank.yanki.service.model.dto.YankiDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IYankiService {

     Flux<Yanki> findAll();

     Mono<YankiDto> save(Yanki yanki);

     Mono<Void> update(String id, Mono<YankiDto> mono);

     Mono<Void> delete(String id);

     Mono<YankiDto> getByYanki(String id);

     Mono<YankiDto> findByPhoneNumber(String phoneNumber);

     Mono<Yanki> getYankiAll(YankiDto yankiDto);

     Mono<YankiDto> getYankiDto(Yanki yanki);

    
}

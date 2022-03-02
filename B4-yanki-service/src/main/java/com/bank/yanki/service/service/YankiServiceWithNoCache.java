package com.bank.yanki.service.service;

import com.bank.yanki.service.model.Yanki;
import com.bank.yanki.service.model.dto.YankiDto;
import com.bank.yanki.service.redis.Util.EntityDtoUtil;
import com.bank.yanki.service.redis.Util.config.CacheConfig;
import com.bank.yanki.service.repository.YankiRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RequiredArgsConstructor
@Service
@AllArgsConstructor
//@ConditionalOnProperty(name = "cache.enabled", havingValue = "false")
public class YankiServiceWithNoCache implements IYankiService {

    //Clorox
    @Autowired
    private  YankiRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Flux<Yanki> findAll() {
        return repository.findAll();
    }

    //@CachePut(cacheNames = CacheConfig.YANKI_CACHE, key = "#id", unless = "#result == null")
    @Override
    public Mono<YankiDto> save(Yanki yanki) {
        return  repository.save(yanki).flatMap(this::getYankiDto);
    }

    //@CacheEvict (cacheNames = CacheConfig.YANKI_CACHE, key = "#id")
    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    //@Cacheable(cacheNames = CacheConfig.YANKI_CACHE, key = "#p0" , unless = "#result == null")
    @Override
    public Mono<YankiDto> getByYanki(String id) {
        return repository.findById(id).map(EntityDtoUtil::toDto);
    }

    //@Cacheable(cacheNames = CacheConfig.YANKI_CACHE, key = "#phoneNumber" , unless = "#result == null")
    @Override
    public Mono<YankiDto> findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).flatMap(this::getYankiDto);
    }

    @Override
    public Mono<Void> update(String id, Mono<YankiDto> mono) {
        return this.repository.findById(id)
                .zipWith(mono)
                .doOnNext(t -> t.getT1().setAmount(t.getT2().getAmount()))
                .map(Tuple2::getT1)
                .flatMap(this.repository::save)
                .then();
    }

    @Override
    public Mono<Yanki> getYankiAll(YankiDto yankiDto) {
        return Mono.just(objectMapper.convertValue(yankiDto, Yanki.class));
    }

    @Override
    public Mono<YankiDto> getYankiDto(Yanki yanki) {
        return Mono.just(objectMapper.convertValue(yanki, YankiDto.class));
    }


}

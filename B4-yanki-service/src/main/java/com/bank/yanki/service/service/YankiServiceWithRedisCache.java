package com.bank.yanki.service.service;

/*
import com.bank.yanki.service.model.dto.YankiDto;
import com.bank.yanki.service.repository.YankiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;*/

//@Service
//@ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
public class YankiServiceWithRedisCache /*extends YankiServiceWithNoCache*/ {
   /* private static final String KEY = "yanki";

    @Autowired
    private ReactiveHashOperations<String, String , YankiDto> hashOperations;



    public Mono<YankiDto> getYanki(String id) {
        return hashOperations.get(KEY, id)
                .switchIfEmpty(this.getFromDatabaseAndCache(id));
    }


    public Mono<Void> updateYanki(String id, Mono<YankiDto> mono) {
        return super.update(id, mono)
                .then(this.hashOperations.remove(KEY, id))
                .then();
    }

    private Mono<YankiDto> getFromDatabaseAndCache(String id) {
        return super.getByYanki(id)
                .flatMap(dto -> this.hashOperations.put(KEY, id, dto)
                        .thenReturn(dto));
    }

    */


}

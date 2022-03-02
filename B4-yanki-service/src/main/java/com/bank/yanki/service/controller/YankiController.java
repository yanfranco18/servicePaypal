package com.bank.yanki.service.controller;


import com.bank.yanki.service.model.PaymentDebitCard;
import com.bank.yanki.service.model.Yanki;
import com.bank.yanki.service.model.dto.YankiDto;
import com.bank.yanki.service.service.IPaymentDebitCardService;
import com.bank.yanki.service.service.IYankiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
/*
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/yanki") */
public class YankiController {

    /*
    private final IYankiService yankiService;

    private final IPaymentDebitCardService debitService;
    

    //Metodo listar, usando response entity para manejar la respuesta del status y la respuesta del body
    @CircuitBreaker(name="yanki", fallbackMethod = "fallback")
    @TimeLimiter(name="yanki")
    @GetMapping
    public Mono<ResponseEntity<Flux<Yanki>>> getYanki(){
        log.info("iniciando lista");
        return Mono.just(
                //manejo de la respuesta http
                ResponseEntity.ok()
                        //mostrar en el body mediante json
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(yankiService.findAll()));
                        //mostrando en el body la respuesta);
    }

    //Metodo para eliminar
    @CircuitBreaker(name="yanki", fallbackMethod = "fallback")
    @TimeLimiter(name="yanki")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete (@PathVariable String id){

        return yankiService.delete(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }


    //Metodo para editar, pasamos por el requestBody el client a modificar
    @CircuitBreaker(name="yanki", fallbackMethod = "fallback")
    @TimeLimiter(name="yanki")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Void>> edit(@RequestBody Mono<YankiDto> yanki, @PathVariable String id){
        //buscamos el id para obtener el client
        return yankiService.update(id,yanki)

                .map(p -> ResponseEntity.created(URI.create("/yanki/".concat(id)))
                        //Modificamos la respeusta en el body con el contentType
                        .contentType(MediaType.APPLICATION_JSON)
                        //Y pasamos el cliente modificado
                        .body(p))
                //para manejar el error si el cliente no existe, y build para generar la respuesta sin cuerpo
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /*
    //metodo crear
    @CircuitBreaker(name="yanki", fallbackMethod = "fallback")
    @TimeLimiter(name="yanki")
    @PostMapping
    public Mono<ResponseEntity<Yanki>> create(@RequestBody Yanki yanki){
        //validamos la fecha en caso venga fecha, asigamos la fecha
        if(yanki.getCreateDate()==null){
            yanki.setCreateDate(new Date());
        }
        //ahora guardamos el cliente, mediante map, cambiamos el flujo de tipo mono a un responseEntity
        return yankiService.save(yanki)
                //mostramos el estado en el http, indicamos la uri del producto se crea
                .map(p -> ResponseEntity.created(URI.create("/yanki/".concat(p.getId())))
                        //Modificamos la respuesta en el body con el contentType
                        .contentType(MediaType.APPLICATION_JSON)
                        //Y pasamos el cliente creado
                        .body(p));
    } */

    /*

    //metodo buscar por id
    @CircuitBreaker(name="yanki", fallbackMethod = "fallback")
    @TimeLimiter(name="yanki")
    @GetMapping("/getById/{id}")
    public Mono<ResponseEntity<YankiDto>> getById(@PathVariable String id){
        return yankiService.getByYanki(id)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @CircuitBreaker(name="yanki", fallbackMethod = "fallback")
    @TimeLimiter(name="yanki")
    @GetMapping("/getPhoneNumber/{phoneNumber}")
    public Mono<ResponseEntity<PaymentDebitCard>> getPhoneNumber(@PathVariable String phoneNumber){
        return debitService.findByPhoneNumber(phoneNumber)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /*
     */

/*
    //metodo para manejar el error
    private String fallback(HttpServerErrorException ex) {
        return "Response 200, fallback method for error:  " + ex.getMessage();
    }

    */


}

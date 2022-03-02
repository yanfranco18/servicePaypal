package com.bank.yanki.service.handler;

import com.bank.yanki.service.model.PaymentDebitCard;
import com.bank.yanki.service.model.Yanki;
import com.bank.yanki.service.model.dto.YankiDto;
import com.bank.yanki.service.service.IPaymentDebitCardService;
import com.bank.yanki.service.service.IYankiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Log4j2
@Component
@RequiredArgsConstructor
public class YankiHandler {

    private final IYankiService service;

    private final IPaymentDebitCardService debitService;

    @Autowired
    private Validator validator;

    @CircuitBreaker(name="yankiservice", fallbackMethod = "fallback")
    @TimeLimiter(name="yankiservice")
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), YankiDto.class);
    }

    @CircuitBreaker(name="yankiservice", fallbackMethod = "fallback")
    @TimeLimiter(name="yankiservice")
    public Mono<ServerResponse> findId(ServerRequest request) {
        String id = request.pathVariable("id");

        return service.getByYanki(id)
                .flatMap(dto -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
                .switchIfEmpty(
                        ErrorResponse.buildBadResponse("The yanki account whith  id: ".concat(id).concat(" not found"), HttpStatus.NOT_FOUND))
                .onErrorResume(throwable ->
                        ErrorResponse.buildBadResponse(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @CircuitBreaker(name="yankiservice", fallbackMethod = "fallback")
    @TimeLimiter(name="yankiservice")
    public Mono<ServerResponse> findByPhoneNumber(ServerRequest request) {
        String phoneNumber = request.pathVariable("phoneNumber");
        return debitService.findByPhoneNumber(phoneNumber)
                .flatMap(dto -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
                .switchIfEmpty(
                        ErrorResponse.buildBadResponse("The yanki account whith  phoneNumber: ".concat(" not found."), HttpStatus.NOT_FOUND))
                .onErrorResume(throwable ->
                        ErrorResponse.buildBadResponse(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @CircuitBreaker(name="yankiservice", fallbackMethod = "fallback")
    @TimeLimiter(name="yankiservice")
    public Mono<ServerResponse> create(ServerRequest request) {
        log.info("ENTRO AL CREATE");
        Mono<Yanki> yanki = request.bodyToMono(Yanki.class);
        PaymentDebitCard pdc= new PaymentDebitCard();
        return yanki.flatMap(m -> {
            Errors errors = new BeanPropertyBindingResult(m, Yanki.class.getName());
            validator.validate(m, errors);

            if (errors.hasErrors()) {
                return Flux.fromIterable(errors.getFieldErrors())
                        .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collectList()
                        .flatMap(list -> ServerResponse.badRequest().body(fromValue(list)));
            } else {
                if (m.getCreateDate() == null) {
                    m.setCreateDate(new Date());
                }
                String idDebitCardNumber=m.getIdDebitCardNumber();
                //obteniendo el id del debito y seteando el contenido
                debitService.getDebitById(idDebitCardNumber).flatMap(qdc->{
                    log.info("getCardNumber: "+ qdc.getId());
                    pdc.setIdDebitCardNumber(qdc.getId());
                    pdc.setPhoneNumber(m.getPhoneNumber());
                    m.setIdDebitCardNumber(qdc.getId());
                    log.info("m.getId(): "+m.getIdDebitCardNumber());
                    return debitService.save(pdc).flatMap(mdb -> ServerResponse
                            .status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromValue(mdb)));
                }).subscribe();

                return service.save(m).flatMap(mdb -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(mdb)));
            }
        });
    }

    @CircuitBreaker(name="yankiservice", fallbackMethod = "fallback")
    @TimeLimiter(name="yankiservice")
    public Mono<ServerResponse> deleteById(ServerRequest request){
        String id = request.pathVariable("id");
        return service.delete(id).then(ServerResponse.noContent().build());
    }

    //metodo para manejar el error
    private String fallback(HttpServerErrorException ex) {
        return "Response 200, fallback method for error:  " + ex.getMessage();
    }


}

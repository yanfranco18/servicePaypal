package com.bank.yanki.service.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Debit implements Serializable {

    private String id;

    private String cardNumber;

    private String idClient;

    private String numberAccount;

    private LocalDate creationDate;

    private LocalDate expirationDate;

    private static final long serialVersionUID = 1L;

}

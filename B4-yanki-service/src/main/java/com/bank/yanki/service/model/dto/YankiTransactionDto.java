package com.bank.yanki.service.model.dto;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class YankiTransactionDto implements Serializable {

    private String id;

    private String originNumber;

    private String destinationNumber;

    private Double amount;

    private Status status;

    private LocalDateTime createAt;

    private String idDebitCardNumber;

    public enum Status {
        PENDING,
        REJECTED,
        SUCCESSFUL
    }
    private static final long serialVersionUID = 1L;
}

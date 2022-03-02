package com.bank.yanki.service.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class PaymentDebitCardDto implements Serializable {

    private String id;
    private String phoneNumber;
    private String idDebitCardNumber;

    private static final long serialVersionUID = 1L;
}

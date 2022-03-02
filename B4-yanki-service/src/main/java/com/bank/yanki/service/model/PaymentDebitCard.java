package com.bank.yanki.service.model;


import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@ToString
@Data
@Document("PaymentDebitCard")
public class PaymentDebitCard implements Serializable {
    @Id
    private String id;
    private String phoneNumber;
    private String idDebitCardNumber;

    private static final long serialVersionUID = 1L;

}

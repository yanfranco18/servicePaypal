package com.bank.yanki.service.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class YankiDto implements Serializable{

    private String id;
    private String identityDocument;
    private String phoneNumber;
    private Double amount;
    private String email;
    private String imeiNumber;
    private Date createDate;
    private String idDebitCardNumber;

    private static final long serialVersionUID = 1L;
}

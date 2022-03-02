package com.bank.yanki.service.model;


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
@Document(collection = "Yanki")
@ToString
public class Yanki implements Serializable{
    @Id
    private String id;
    private String identityDocument;
    private String phoneNumber;
    private Double amount;
    private String email;
    private String imeiNumber;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createDate;
    private String idDebitCardNumber;

    private static final long serialVersionUID = 1L;
}

package com.bank.yanki.service.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {
    private String id;
    private String nameClient;
    private String typeClient;
    private String identityDocument;
    private String phoneNumber;
    private Date createDate;

    private static final long serialVersionUID = 1L;
}

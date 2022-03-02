package com.bank.yanki.service.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Account implements Serializable {
    private String id;
    private String number;
    private Double lineAvailable;
    private Double lineUsed;
    private Double balancePast;
    private Double amount;
    private Date createDate;
    private Integer countMovements;
    private String idClient;
    private String idProducts;

    private static final long serialVersionUID = 1L;
}

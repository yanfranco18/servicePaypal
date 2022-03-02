package com.bank.yanki.service.redis.Util;

import com.bank.yanki.service.model.Yanki;
import com.bank.yanki.service.model.dto.YankiDto;

public class EntityDtoUtil {
    public static YankiDto toDto(Yanki yanki){
        YankiDto dto = new YankiDto();
        dto.setId(yanki.getId());
        dto.setIdentityDocument(yanki.getIdentityDocument());
        dto.setPhoneNumber(yanki.getPhoneNumber());
        dto.setAmount(yanki.getAmount());
        dto.setEmail(yanki.getEmail());
        dto.setImeiNumber(yanki.getImeiNumber());
        dto.setCreateDate(yanki.getCreateDate());
        return dto;
    }

    public static Yanki toEntity(YankiDto dto){
        Yanki yanki = new Yanki();
        yanki.setId(dto.getId());
        yanki.setIdentityDocument(dto.getIdentityDocument());
        yanki.setPhoneNumber(dto.getPhoneNumber());
        yanki.setAmount(dto.getAmount());
        yanki.setEmail(dto.getEmail());
        yanki.setImeiNumber(dto.getImeiNumber());
        yanki.setCreateDate(dto.getCreateDate());
        return yanki;
    }
}

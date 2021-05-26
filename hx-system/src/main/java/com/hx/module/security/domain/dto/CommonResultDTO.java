package com.hx.module.security.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResultDTO<T> {
    T data;
    Long code;
    String  message;

    public CommonResultDTO() {}

    public CommonResultDTO(T data, long code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public CommonResultDTO (T data, String message) {
        this(data,0L,message);
    }

    public CommonResultDTO (T data) {
         this(data,null);
    }
}

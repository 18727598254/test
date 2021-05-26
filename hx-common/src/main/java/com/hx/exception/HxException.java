package com.hx.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 统一异常处理
 */
@Getter
public class HxException extends RuntimeException {

    private Integer status = 400;

    public HxException(String msg) {
        super(msg);
    }

    public HxException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}


package com.hx.module.security.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class AuthUserDTO {

    @NotBlank
    private String account;

    @NotBlank
    private String password;

    private String code;

    private String uuid;

    @Override
    public String toString() {
        return "{account=" + account  + ", password= ******}";
    }
}

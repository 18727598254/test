package com.hx.module.security.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PowerSettingItemDTO extends  BasePowerItemDTO{
    List<AllPowersResultDTO> functions;
}

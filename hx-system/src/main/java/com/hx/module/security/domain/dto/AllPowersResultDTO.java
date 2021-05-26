package com.hx.module.security.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllPowersResultDTO {
    String id;
    String name; // 权限名称
    String area; // 区域
    String areaDesc; // 区域描述
    String controller; // 控制器
    String controllerDesc; // 控制器描述
    String action; // Action
    String actionDesc; // Action描述
    String code; // code 权限code码
}

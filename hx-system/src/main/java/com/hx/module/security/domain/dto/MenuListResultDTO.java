package com.hx.module.security.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuListResultDTO {
    List<MenuListResultDTO> children; // 子菜单
    String creationTime; // 创间时间
    String parentName; // 父级角色名称
    List<PowerSettingItemDTO> powers; // 当前菜单对应的权限
}

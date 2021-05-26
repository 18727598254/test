package com.hx.module.security.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuBasicInfoDTO {

    String id ; // 菜单Id
    String parentId; // 父级Id
    String name; // 菜单名称
    String icon; // 图标
    String url; // 路由地址
    String indexOrder; // 菜单序号
    String remark; // 备注
}

package com.hx.module.security.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddNewRolesResultDTO {

    String id;
    String name;
    String remark; // 备注
    boolean isDefault; // 是否默认
    boolean isLocked; // 是否锁定
    String creationTime; // 创建时间
    String creatorId; // 创建者id
    String creatorName; // 创建者名称
}

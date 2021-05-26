package com.hx.module.system.domain.dto;

import com.hx.base.BaseDTO;
import com.hx.module.security.domain.dto.PowerSettingItemDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author lb
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDTO extends BaseDTO implements Serializable {

    private Long id;

    private List<MenuDTO> children;

    private Integer type;

    private String permission;

    private String title;

    private Integer menuSort;

    private String path;

    private String component;

    private Long pid;

    private Integer subCount;

    private Boolean iFrame;

    private Boolean cache;

    private Boolean hidden;

    private String componentName;

    private String icon;

    String parentName; // 父级角色名称
    List<PowerSettingItemDTO> powers; // 当前菜单对应的权限

    public Boolean getHasChildren() {
        return subCount > 0;
    }

    public Boolean getLeaf() {
        return subCount <= 0;
    }

    public String getLabel() {
        return title;
    }

}

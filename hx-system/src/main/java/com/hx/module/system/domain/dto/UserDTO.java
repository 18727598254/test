package com.hx.module.system.domain.dto;

import com.hx.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends BaseDTO implements Serializable {

    private Long id;

    private Set<RoleSmallDTO> roles;

    private Set<JobSmallDTO> jobs;

    private DeptSmallDTO dept;

    private Long deptId;

    private String name;

    private String nickName;

    private String email;

    private String phone;

    private String sex;

    private String avatarName;

    private String headProtrait;

    @JsonIgnore
    private String password;

    private Boolean enabled;

    @JsonIgnore
    private Boolean isAdmin;

    private Date pwdResetTime;
}

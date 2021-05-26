package com.hx.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 验证码业务场景
 * </p>
 */
@Getter
@AllArgsConstructor
public enum CodeBiEnum {

    /* 旧邮箱修改邮箱 */
    ONE(1, "旧邮箱修改邮箱"),

    /* 通过邮箱修改密码 */
    TWO(2, "通过邮箱修改密码");

    private final int code;
    private final String description;

    public static CodeBiEnum find(int code) {
        for (CodeBiEnum value : CodeBiEnum.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        return null;
    }

}

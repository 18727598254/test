package com.hx.module.system.domain.dto;

import java.io.Serializable;

public class JsonWebTokenDTO implements Serializable {

    /**
     * 获取或设置 用于业务身份认证的AccessToken
     */
    public String accessToken ;
    /**
     * 获取或设置 用于刷新AccessToken的RefreshToken
     */
    public String refreshToken;
    /**
     * Token过期时间
     */
    public String tokenUtcExpires;
    /**
     * 获取或设置 RefreshToken有效期，UTC标准
     */
    public String refreshUctExpires;

    public JsonWebTokenDTO() {
    }

    public JsonWebTokenDTO(String accessToken, String refreshToken, String tokenUtcExpires, String refreshUctExpires) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenUtcExpires = tokenUtcExpires;
        this.refreshUctExpires = refreshUctExpires;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public String getTokenUtcExpires() {
        return this.tokenUtcExpires;
    }

    public String getRefreshUctExpires() {
        return this.refreshUctExpires;
    }

    public String toString() {
        return "JsonWebTokenDTO(accessToken=" + this.getAccessToken() + ", refreshToken=" + this.getRefreshToken() + ", tokenUtcExpires=" + this.getTokenUtcExpires() + ", refreshUctExpires=" + this.getRefreshUctExpires() + ")";
    }
}


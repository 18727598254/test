package com.hx.config;

import com.hx.util.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lb
 */
@Service(value = "hxPermissionConfig")
public class HxPermissionConfig {

    public boolean check(String ...permissions){
        // 获取当前用户的所有权限
        List<String> hxPermissions = SecurityUtils.getCurrentUser().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return hxPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(hxPermissions::contains);
    }
}


package com.hx.module.system.service;

import com.hx.module.system.domain.dto.UserDTO;

import java.util.List;

/**
 * 数据权限服务类

 */
public interface DataService {

    /**
     * 获取数据权限
     * @param user /
     * @return /
     */
    List<Long> getDeptIds(UserDTO user);
}

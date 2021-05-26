package com.hx.config;

import com.hx.util.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @描述 : 设置审计 统一处理创建人，创建时间，修改人，修改时间

 */
@Component("auditorAware")
public class AuditorConfig implements AuditorAware<String> {

    /**
     * 返回操作员标志信息
     *
     * @return /
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        // 这里应根据实际业务情况获取具体信息
        return Optional.of(SecurityUtils.getCurrentUsername());
    }
}

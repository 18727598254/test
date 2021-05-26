package com.hx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    /**
     * 文件大小限制
     */
    private Long maxSize;

    /**
     * 头像大小限制
     */
    private Long avatarMaxSize;

    private HxPath mac;

    private HxPath linux;

    private HxPath windows;

    public HxPath getPath() {
        String os = System.getProperty("os.name");
        String win = "win";
        String macName = "mac";
        if (os.toLowerCase().startsWith(win)) {
            return windows;
        } else if (os.toLowerCase().startsWith(macName)) {
            return mac;
        }
        return linux;
    }

    @Data
    public static class HxPath {

        private String path;

        private String avatar;
    }
}

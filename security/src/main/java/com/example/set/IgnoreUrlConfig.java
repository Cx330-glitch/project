package com.example.set;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Component
@ConfigurationProperties(prefix = "dependencies")
public class IgnoreUrlConfig {

    /**
     * SWAGGER参数
     */
    private final Swagger swagger = new Swagger();

    private  final Nocheck nocheck = new Nocheck();

    /**
     * SWAGGER接口文档参数
     */
    @Data
    public static class Swagger {
        private String title;
        private String description;
        private String version;
        private String termsOfServiceUrl;
        private String contactName;
        private String contactUrl;
        private String contactEmail;
        private String license;
        private String licenseUrl;
    }

    @Data

    public static class Nocheck {

        private Integer tokenExpireTime;
        private Integer saveLoginTime;
        private Integer loginTimeLimit;
        private Integer loginAfterTime;
        private List<String> ignoreUrls;
    }

}

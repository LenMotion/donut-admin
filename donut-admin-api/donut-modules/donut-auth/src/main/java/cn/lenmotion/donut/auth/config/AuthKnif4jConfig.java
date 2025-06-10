package cn.lenmotion.donut.auth.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lenmotion
 */
@Configuration
public class AuthKnif4jConfig {

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("01-登陆授权")
                .packagesToScan("cn.lenmotion.donut.auth.controller")
                .build();
    }

}

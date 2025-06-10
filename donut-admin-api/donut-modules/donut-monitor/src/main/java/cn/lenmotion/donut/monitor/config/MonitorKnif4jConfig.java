package cn.lenmotion.donut.monitor.config;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lenmotion
 */
@Configuration
public class MonitorKnif4jConfig {

    @Bean
    public GroupedOpenApi monitorApi(OperationCustomizer operationCustomizer) {
        return GroupedOpenApi.builder()
                .group("03-系统监控")
                .packagesToScan("cn.lenmotion.donut.monitor.controller")
                .addOperationCustomizer(operationCustomizer)
                .build();
    }

}

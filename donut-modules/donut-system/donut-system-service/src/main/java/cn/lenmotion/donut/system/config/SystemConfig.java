package cn.lenmotion.donut.system.config;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author lenmotion
 */
@EnableFileStorage
@EnableWebSocket
@Configuration
public class SystemConfig {

    /**
     * 系统管理模块API文档
     * @param operationCustomizer
     * @return
     */
    @Bean
    public GroupedOpenApi systemApi(OperationCustomizer operationCustomizer) {
        return GroupedOpenApi.builder()
                .group("02-系统管理")
                .packagesToScan("cn.lenmotion.donut.system.controller")
                .addOperationCustomizer(operationCustomizer)
                .build();
    }

    // websocket
    @Bean
    public ServerEndpointExporter serverEndpointExporter () {
        return new ServerEndpointExporter();
    }

}

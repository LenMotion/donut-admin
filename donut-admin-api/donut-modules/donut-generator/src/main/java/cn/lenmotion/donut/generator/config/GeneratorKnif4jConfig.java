package cn.lenmotion.donut.generator.config;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lenmotion
 */
@Configuration
public class GeneratorKnif4jConfig {

    @Bean
    public GroupedOpenApi generatorApi(OperationCustomizer operationCustomizer) {
        return GroupedOpenApi.builder()
                .group("04-代码生成")
                .packagesToScan("cn.lenmotion.donut.generator.controller")
                .addOperationCustomizer(operationCustomizer)
                .build();
    }

}

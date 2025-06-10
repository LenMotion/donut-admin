package cn.lenmotion.donut.framework.config;

import cn.lenmotion.donut.framework.template.JacksonRedisTemplate;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author lenmotion
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    @Primary
    public JacksonRedisTemplate jacksonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        JacksonRedisTemplate jacksonRedisTemplate = new JacksonRedisTemplate(this.getJackson2JsonRedisSerializer());
        // 缓存支持回滚(事务管理)
        jacksonRedisTemplate.setEnableTransactionSupport(true);
        jacksonRedisTemplate.setConnectionFactory(redisConnectionFactory);
        jacksonRedisTemplate.afterPropertiesSet();
        return jacksonRedisTemplate;
    }

    /**
     * 缓存过期时间自定义配置
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<String> jackson2JsonRedisSerializer = this.getJackson2JsonRedisSerializer();
        // 初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .entryTtl(Duration.ofHours(2));
        return RedisCacheManager
                .builder(redisCacheWriter)
                .cacheDefaults(defaultCacheConfig)
                .build();
    }

    private Jackson2JsonRedisSerializer<String> getJackson2JsonRedisSerializer() {
        // 设置CacheManager的值序列化方式为Jackson2JsonRedisSerializer,默认就是使用StringRedisSerializer序列化key,JdkSerializationRedisSerializer序列化value
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        return new Jackson2JsonRedisSerializer<>(objectMapper, String.class);
    }

}

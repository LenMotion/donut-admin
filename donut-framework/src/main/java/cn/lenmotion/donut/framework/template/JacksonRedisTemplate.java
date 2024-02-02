package cn.lenmotion.donut.framework.template;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author lenmotion
 */
public class JacksonRedisTemplate extends RedisTemplate<String, String>  {

    public JacksonRedisTemplate(Jackson2JsonRedisSerializer<String> redisSerializer) {
        this.setKeySerializer(RedisSerializer.string());
        this.setValueSerializer(redisSerializer);
        this.setHashKeySerializer(RedisSerializer.string());
        this.setHashValueSerializer(redisSerializer);
    }

}

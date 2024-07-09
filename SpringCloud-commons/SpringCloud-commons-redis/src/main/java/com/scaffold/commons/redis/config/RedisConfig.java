package com.scaffold.commons.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Configuration
public class RedisConfig {
    @Resource
    private RedisConnectionFactory factory;

    @Bean("redisStringTemplate")
    public RedisTemplate<String, String> redisStringTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        //key序列化方式
        redisTemplate.setKeySerializer(redisSerializer);
        //value序列化
        redisTemplate.setValueSerializer(redisSerializer);
        //value hashmap序列化
        redisTemplate.setHashValueSerializer(redisSerializer);
        //key hashmap序列化
        redisTemplate.setHashKeySerializer(redisSerializer);
        return redisTemplate;
    }
}

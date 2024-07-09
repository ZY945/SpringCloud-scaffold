package com.scaffold.user;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RedisTemplateTest {
    @Resource(name = "redisStringTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void add() {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        Map<String, String> map = new HashMap<>();
        map.put("dev", "dev-localhost");
        map.put("test", "test-localhost");
        map.put("pro", "pro-localhost");

        opsForHash.putAll("env", map);
    }


    @Test
    public void putAll() {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        Map<String, String> map = new HashMap<>();
        map.put("dev", null);
        map.put("test", "");
        map.put("pro", " ");

        opsForHash.putAll("env", map);
    }

    @Test
    public void delAndPutAll() {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
//        opsForHash.delete("env", "dev", "test", "pro");
        opsForHash.delete("env", "dev");
        opsForHash.delete("env", "test");
        opsForHash.delete("env", "pro");
        Map<String, String> map = new HashMap<>();
        map.put("dev", null);
        map.put("test", "");
        map.put("pro", " ");

        opsForHash.putAll("env", map);
    }

    @Test
    public void get() {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();


        System.out.println(opsForHash.get("env", "dev"));
        System.out.println(opsForHash.get("env", "test"));
        System.out.println(opsForHash.get("env", "pro"));

    }
}

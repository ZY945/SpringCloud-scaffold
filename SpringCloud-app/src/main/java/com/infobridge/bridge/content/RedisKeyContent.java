package com.infobridge.bridge.content;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class RedisKeyContent {

    @Value("${redis.key.user.userLocationKey}")
    private String userLocationKey;

    @Value("${redis.key.user.userMoodKey}")
    private String userMoodKey;

}

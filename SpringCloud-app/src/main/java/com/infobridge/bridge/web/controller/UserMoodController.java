package com.infobridge.bridge.web.controller;

import com.infobridge.bridge.content.RedisKeyContent;
import com.infobridge.bridge.web.service.RedisGeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mood")
public class UserMoodController {

    @Autowired
    private RedisKeyContent redisKeyContent;

    @Autowired
    private RedisGeoService redisGeoService;

}

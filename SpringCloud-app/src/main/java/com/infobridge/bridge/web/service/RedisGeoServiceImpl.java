package com.infobridge.bridge.web.service;


import com.scaffold.commons.redis.dto.GeoDTO;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisGeoServiceImpl implements RedisGeoService {
    @Resource(name = "redisStringTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加用户的经纬度信息
     *
     * @param userLocationKey 用户位置信息键
     * @param longitude       经度
     * @param latitude        纬度
     * @param member          用户标识
     */
    @Override
    public Long addPoint(String userLocationKey, double longitude, double latitude, String member) {
        return redisTemplate.opsForGeo().add(userLocationKey,
                new Point(longitude, latitude), member);
    }

    /**
     * (最新位置)通过经纬度在指定范围内查询附近的用户位置信息
     *
     * @param userLocationKey 用户位置信息键
     */
    @Override
    public GeoResults<GeoLocation<Object>> findNearbyPoints(String userLocationKey, GeoDTO geoDTO) {
        Distance distance = new Distance(geoDTO.getRadius(), geoDTO.getMetrics());
        Point point = new Point(geoDTO.getLongitude(), geoDTO.getLatitude());
        Circle circle = new Circle(point, distance);
        int limitMember = geoDTO.getLimitMember() == null ? 10 : geoDTO.getLimitMember();
        // 使用Redis的地理位置操作对象，在指定范围内查询附近的用户位置信息
        return redisTemplate
                .opsForGeo()
                .radius(userLocationKey, circle, RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeCoordinates().limit(limitMember));
    }

    /**
     * 获取某个成员的地理位置信息
     *
     * @param userLocationKey 用户位置信息键
     * @param member          用户标识
     */
    @Override
    public GeoResults<GeoLocation<Object>> getGeoByMember(String userLocationKey, String member) {
        return redisTemplate
                .opsForGeo()
                .radius(userLocationKey, member, new Distance(0, Metrics.KILOMETERS));
    }

    /**
     * (不是最新的位置)根据memberID来获取指定范围内的用户
     *
     * @param userLocationKey 用户位置信息键
     * @param member          用户标识
     */
    @Override
    public GeoResults<GeoLocation<Object>> findNearbyMember(String userLocationKey, String member, GeoDTO geoDTO) {
        Distance distance = new Distance(geoDTO.getRadius(), geoDTO.getMetrics());
        int limitMember = geoDTO.getLimitMember() == null ? 10 : geoDTO.getLimitMember();
        RedisGeoCommands.GeoRadiusCommandArgs radiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeCoordinates().limit(limitMember);
        // 使用Redis的地理位置操作对象，在指定范围内查询附近的用户位置信息
        return redisTemplate
                .opsForGeo()
                .radius(userLocationKey, member, distance, radiusCommandArgs);
    }
}

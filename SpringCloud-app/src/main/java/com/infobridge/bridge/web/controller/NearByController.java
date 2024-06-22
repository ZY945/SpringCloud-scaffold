package com.infobridge.bridge.web.controller;

import com.infobridge.bridge.content.RedisKeyContent;
import com.infobridge.bridge.web.service.RedisGeoService;
import com.scaffold.commons.redis.dto.GeoDTO;
import com.scaffold.commons.redis.dto.NearByDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/nearby")
public class NearByController {

    @Autowired
    private RedisKeyContent redisKeyContent;

    @Autowired
    private RedisGeoService redisGeoService;

    /**
     * 更新用户位置信息
     *
     * @param dto
     * @return
     */
    @PostMapping("/location/update")
    public Long updateUserLocation(@Validated @RequestBody NearByDTO dto) {
        Assert.notNull(dto.getUserId(), "用户ID(UserId)不能为空");
        Assert.notNull(dto.getLongitude(), "经度(Longitude)不能为空");
        Assert.notNull(dto.getLatitude(), "纬度(Latitude)不能为空");
        String userId = dto.getUserId();
        double longitude = Double.parseDouble(dto.getLongitude());
        double latitude = Double.parseDouble(dto.getLatitude());
        // 使用Redis的地理位置操作对象，将用户的经纬度信息添加到指定的key中
        return redisGeoService.addPoint(redisKeyContent.getUserLocationKey(), longitude, latitude, userId);
    }

    /**
     * 获取附近的人
     *
     * @param dto
     * @return
     */
    @GetMapping("/location/neighborhoodByPoint")
    public List<Object> neighborhoodByPoint(@Validated @RequestBody NearByDTO dto) {
        Assert.notNull(dto.getRadius(), "半径(Radius)不能为空");
        Assert.notNull(dto.getLongitude(), "经度(Longitude)不能为空");
        Assert.notNull(dto.getLatitude(), "纬度(Latitude)不能为空");

        String userId = dto.getUserId();
        double radius = Double.parseDouble(dto.getRadius());
        double longitude = Double.parseDouble(dto.getLongitude());
        double latitude = Double.parseDouble(dto.getLatitude());
        GeoDTO geoDTO = GeoDTO.builder()
                .longitude(longitude)
                .latitude(latitude)
                .radius(radius)
                .metrics(Metrics.KILOMETERS)
                .limitMember(Integer.parseInt(dto.getLimitMember())).build();
        if (!Objects.isNull(userId)) {
            redisGeoService.addPoint(redisKeyContent.getUserLocationKey(), longitude, latitude, userId);
        }
        // 使用Redis的地理位置操作对象，在指定范围内查询附近的用户位置信息
        GeoResults<GeoLocation<Object>> geoResults = redisGeoService.findNearbyPoints(redisKeyContent.getUserLocationKey(), geoDTO);

        List<Object> nearbyUsers = new ArrayList<>();
        for (GeoResult<RedisGeoCommands.GeoLocation<Object>> geoResult : geoResults.getContent()) {
            Object memberId = geoResult.getContent().getName();
            // 排除查询用户本身
            if (!memberId.equals(userId)) {
                nearbyUsers.add(memberId);
            }
        }
        return nearbyUsers;
    }

    /**
     * 获取附近的人
     *
     * @param dto
     * @return
     */
    @GetMapping("/location/neighborhoodByMember")
    public List<Object> neighborhoodByMember(@Validated @RequestBody NearByDTO dto) {
        Assert.notNull(dto.getRadius(), "半径不能为空");
        Assert.notNull(dto.getUserId(), "用户ID不能为空");

        String userId = dto.getUserId();
        double radius = Double.parseDouble(dto.getRadius());
        GeoDTO geoDTO = GeoDTO.builder()
                .radius(radius)
                .metrics(Metrics.KILOMETERS)
                .limitMember(Integer.parseInt(dto.getLimitMember())).build();
        // 使用Redis的地理位置操作对象，在指定范围内查询附近的用户位置信息
        GeoResults<GeoLocation<Object>> geoResults = redisGeoService.findNearbyMember(redisKeyContent.getUserLocationKey(), userId, geoDTO);

        List<Object> nearbyUsers = new ArrayList<>();
        for (GeoResult<RedisGeoCommands.GeoLocation<Object>> geoResult : geoResults.getContent()) {
            Object memberId = geoResult.getContent().getName();
            // 排除查询用户本身
            if (!memberId.equals(userId)) {
                nearbyUsers.add(memberId);
            }
        }
        return nearbyUsers;
    }
}

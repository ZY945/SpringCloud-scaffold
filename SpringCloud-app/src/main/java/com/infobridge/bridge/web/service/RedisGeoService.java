package com.infobridge.bridge.web.service;

import com.scaffold.commons.redis.dto.GeoDTO;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;

public interface RedisGeoService {

    Long addPoint(String userLocationKey, double longitude, double latitude, String member);

    GeoResults<RedisGeoCommands.GeoLocation<Object>> findNearbyPoints(String userLocationKey, GeoDTO geoDTO);

    GeoResults<RedisGeoCommands.GeoLocation<Object>> getGeoByMember(String userLocationKey, String member);

    GeoResults<RedisGeoCommands.GeoLocation<Object>> findNearbyMember(String userLocationKey, String userId, GeoDTO geoDTO);
}

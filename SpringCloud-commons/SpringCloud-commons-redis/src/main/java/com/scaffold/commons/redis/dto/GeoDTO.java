package com.scaffold.commons.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.domain.geo.Metrics;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GeoDTO {

    private double longitude;

    private double latitude;

    private double radius;

    private Integer limitMember;

    private Metrics metrics;
}

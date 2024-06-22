package com.scaffold.commons.redis.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class NearByDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String radius;

    private String longitude;

    private String latitude;

    private String limitMember;

}

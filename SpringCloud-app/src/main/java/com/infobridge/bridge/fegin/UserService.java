package com.infobridge.bridge.fegin;


import com.scaffold.commons.utils.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "user-service", url = "${info-bridge-feign.user-service-url}", fallback = UserServiceFallback.class)
public interface UserService {

    @PostMapping(value = "/hello/1", consumes = "application/json")
    Result hello1();

    @PostMapping(value = "/hello/2", consumes = "application/json")
    Result hello2(Map<String, Object> params);

    @PostMapping(value = "/hello/3", consumes = "application/json")
    Result hello3(Map<String, Object> params);
}

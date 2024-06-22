package com.infobridge.bridge.fegin;


import com.scaffold.commons.utils.vo.Result;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserServiceFallback implements UserService {

    @Override
    public Result hello1() {
        return Result.error("服务错误");
    }

    @Override
    public Result hello2(Map<String, Object> params) {
        return Result.error("服务错误");
    }

    @Override
    public Result hello3(Map<String, Object> params) {
        return Result.error("服务错误");
    }
}

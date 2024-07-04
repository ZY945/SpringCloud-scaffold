package com.scaffold.app.web.controller;

import com.scaffold.app.fegin.UserService;
import com.scaffold.commons.utils.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private UserService userService;

    @GetMapping("/hello")
    public Result<?> hello() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", "zhangsan");
        Result<?> hello2 = userService.hello2(params);
        System.out.println(hello2);
        return hello2;
    }
}

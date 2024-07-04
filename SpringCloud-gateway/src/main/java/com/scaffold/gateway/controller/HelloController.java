package com.scaffold.gateway.controller;


import com.scaffold.commons.utils.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * 测试
     */
    @PostMapping("/1")
    public Result<?> hello1() {
        return Result.success("测试");
    }
}



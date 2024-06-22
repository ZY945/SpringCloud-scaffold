package com.infobridge.bridge.web.controller;


import com.infobridge.bridge.empty.dto.HelloDTO;
import com.scaffold.commons.utils.vo.Result;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


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

    /**
     * 测试
     */
    @PostMapping("/2")
    public Result<?> hello2(@RequestBody HelloDTO dto) {
        return Result.success("测试+" + dto.getName());
    }


    /**
     * 测试
     */
    @PostMapping("/3")
    public Result<?> hello3(@RequestBody HelloDTO dto) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return Result.success("测试请求头拦截器+" + request.getHeader("app-token"));
    }

}



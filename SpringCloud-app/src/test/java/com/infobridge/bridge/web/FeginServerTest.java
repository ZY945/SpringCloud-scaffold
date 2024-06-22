package com.infobridge.bridge.web;


import com.infobridge.bridge.fegin.UserService;
import com.scaffold.commons.utils.vo.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@SpringBootTest
public class FeginServerTest {

    @Resource
    private UserService userService;

    @Test
    void hello() {

        Result<?> hello = userService.hello1();
        System.out.println(hello);
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", "zhangsan");
        Result<?> hello2 = userService.hello2(params);
        System.out.println(hello2);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        request.setAttribute("app-token", "123456");
        Result<?> hello3 = userService.hello3(params);
        System.out.println(hello3);
    }
}

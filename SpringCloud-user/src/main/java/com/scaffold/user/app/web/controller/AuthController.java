package com.scaffold.user.app.web.controller;

import com.scaffold.commons.aop.LogAOP;
import com.scaffold.commons.utils.vo.Result;
import com.scaffold.user.empty.dto.AuthDTO;
import com.scaffold.user.empty.vo.AuthUserVO;
import org.assertj.core.util.Lists;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author dongfeng
 * 2024-07-04 23:01
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping(path = "/user")
    @LogAOP(moduleName = "RedisTemplateTest", printTime = false)
    public Result<AuthUserVO> authUser(@RequestBody AuthDTO authDTO) {
        AuthUserVO authUserVO = new AuthUserVO();
        if (!Objects.isNull(authDTO.getToken())) {
            authUserVO.setRoles(Lists.newArrayList("admin", "user"));
        }
        return Result.success(authUserVO);
    }
}

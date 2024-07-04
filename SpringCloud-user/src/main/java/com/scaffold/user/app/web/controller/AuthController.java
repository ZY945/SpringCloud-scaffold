package com.scaffold.user.app.web.controller;

import com.scaffold.user.empty.dto.AuthDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongfeng
 * 2024-07-04 23:01
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/user")
    public String authUser(@RequestBody AuthDTO authDTO) {
        return "admin:" + authDTO.getToken();
    }
}

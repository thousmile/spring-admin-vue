package com.ifsaid.admin.controller;

import com.ifsaid.admin.service.ISysUserService;
import com.ifsaid.admin.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 10:49
 * @describe：
 * @version: 1.0
 */

@RestController
public class AuthController {

    @Autowired
    private ISysUserService sysUserService;

    @PostMapping(value = "${jwt.route.login}")
    public Result<String> login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Result.error401("用户或者密码不能为空！", null);
        }
        return Result.success("登录成功", sysUserService.login(username, password));
    }

    @PostMapping(value = "${jwt.route.refresh}")
    public Result<String> refresh(@RequestHeader("${jwt.header}") String token) {
        return Result.success("刷新token成功!", sysUserService.refreshToken(token));
    }

}

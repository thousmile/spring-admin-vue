package com.xaaef.shark.controller;

import com.xaaef.shark.common.jwt.JwtLoginUser;
import com.xaaef.shark.common.jwt.JwtTokenUtils;
import com.xaaef.shark.service.UserLoginService;
import com.xaaef.shark.service.VerifyCodeService;
import com.xaaef.shark.util.JsonResult;
import com.xaaef.shark.util.SecurityUtils;
import com.xaaef.shark.vo.LoginUser;
import com.xaaef.shark.vo.TokenValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 用户认证 控制器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */


@Slf4j
@Api(tags = "[ 权限管理 ] 用户认证")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @ApiOperation(value = "用户登录认证", notes = "用户名，密码登录格式 {\"username\":\"admin\",\"password\":\"admin\"}")
    @PostMapping("/login")
    public JsonResult<TokenValue> login(@RequestBody @Validated LoginUser user, BindingResult br) {
        // 根据 CodeKey 从 redis 中获取到 ServerCodeText
        if (!verifyCodeService.checkVerifyCode(user.getCodeKey(), user.getCodeText())) {
            return JsonResult.fail("验证码错误！");
        }
        try {
            TokenValue tokenValue = userLoginService.login(user.getUsername(), user.getPassword());
            // 登录成功后，就从 redis 中删除验证码
            verifyCodeService.deleteImageVerifyCode(user.getCodeKey());
            return JsonResult.success("登录成功", tokenValue);
        } catch (LockedException ex) {
            log.error(ex.getMessage());
            return JsonResult.fail("此用户被锁定！暂时无法登录，请联系管理员！");
        } catch (AuthenticationException ex) {
            log.error(ex.getMessage());
            return JsonResult.fail("用户名或密码错误");
        }
    }


    @ApiOperation(value = "用户退出登录", notes = "用户退出登录")
    @GetMapping("/logout")
    public JsonResult<TokenValue> logout() {
        userLoginService.logout();
        return JsonResult.success();
    }


    @ApiOperation(value = "刷新Token值", notes = "只需要在请求头中附带token即可")
    @GetMapping("/refresh")
    public JsonResult<TokenValue> refresh() {
        TokenValue refresh = userLoginService.refresh();
        return JsonResult.success("刷新token成功", refresh);
    }


    @ApiOperation(value = "获取图形验证码", notes = "获取图形验证码, codeKey 前端传入一个随机生成的字符串")
    @GetMapping("/verify/code/{codeKey}")
    public void imageVerifyCode(@PathVariable String codeKey, HttpServletResponse response) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 获取 图片 验证码
        ImageIO.write(
                verifyCodeService.randomImageVerifyCode(codeKey),
                "JPEG",
                response.getOutputStream()
        );
    }

}

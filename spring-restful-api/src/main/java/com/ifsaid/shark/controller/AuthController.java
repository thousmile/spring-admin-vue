package com.ifsaid.shark.controller;

import com.ifsaid.shark.common.jwt.JwtTokenUtils;
import com.ifsaid.shark.common.jwt.JwtUser;
import com.ifsaid.shark.service.SysUserService;
import com.ifsaid.shark.service.VerifyCodeService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.vo.LoginUser;
import com.ifsaid.shark.vo.TokenValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 用户认证 控制器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Api(tags = "[ 权限管理 ] 用户认证")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

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
            String jwtToken = sysUserService.login(user.getUsername(), user.getPassword());
            TokenValue tokenValue = TokenValue.builder()
                    .header(jwtTokenUtils.getTokenHeader())
                    .value(jwtToken)
                    .prefix(jwtTokenUtils.getTokenHead())
                    .expiration(jwtTokenUtils.getExpiration())
                    .build();
            // 登录成功后，就从 redis 中删除验证码
            verifyCodeService.deleteImageVerifyCode(user.getCodeKey());
            return JsonResult.success("登录成功", tokenValue);
        } catch (AuthenticationException ex) {
            log.error(ex.getMessage());
            return JsonResult.fail("用户名或密码错误");
        }
    }


    @ApiOperation(value = "用户退出登录", notes = "用户退出登录")
    @GetMapping("/logout")
    public JsonResult<TokenValue> logout() {
        JwtUser loginUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUser != null && !StringUtils.isEmpty(loginUser.getUsername())) {
            sysUserService.logout(loginUser);
            return JsonResult.success();
        }
        return JsonResult.success();
    }


    @ApiOperation(value = "刷新Token值", notes = "只需要在请求头中附带token即可")
    @GetMapping("/refresh")
    public JsonResult<TokenValue> refresh(@RequestHeader(value = "${jwt.tokenHeader}") String completeToken) {
        // 从完整的token中截取出token值
        String oldToken = jwtTokenUtils.interceptCompleteToken(completeToken);
        // 根据token值，获取登录的用户名
        String username = jwtTokenUtils.getUsernameFromToken(oldToken);
        if (!StringUtils.isEmpty(username)) {
            // 校验数据中的，用户是否存在
            JwtUser details = sysUserService.validateUsername(username);
            if (details != null && !StringUtils.isEmpty(details.getUsername())) {
                // 刷新 token 的值
                String jwtToken = jwtTokenUtils.refreshToken(oldToken);
                // 封装新的 token 值
                TokenValue tokenValue = TokenValue.builder()
                        .header(jwtTokenUtils.getTokenHeader())
                        .value(jwtToken)
                        .prefix(jwtTokenUtils.getTokenHead())
                        .expiration(jwtTokenUtils.getExpiration())
                        .build();
                return JsonResult.success("刷新token成功！", tokenValue);
            }
        }
        return JsonResult.fail("token格式错误!");
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

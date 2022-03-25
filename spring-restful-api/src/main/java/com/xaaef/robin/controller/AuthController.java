package com.xaaef.robin.controller;

import com.xaaef.robin.enums.OAuth2Error;
import com.xaaef.robin.exception.JwtAuthException;
import com.xaaef.robin.jwt.JwtTokenValue;
import com.xaaef.robin.service.JwtTokenService;
import com.xaaef.robin.service.UserLoginService;
import com.xaaef.robin.service.VerifyCodeService;
import com.xaaef.robin.util.JsonResult;
import com.xaaef.robin.vo.LoginUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * <p>
 * 用户认证 控制器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 3.0
 */


@Slf4j
@Api(tags = "[ 权限管理 ] 用户认证")
@RequestMapping("auth")
@RestController
@AllArgsConstructor
public class AuthController {

    private UserLoginService userLoginService;

    private JwtTokenService jwtTokenService;

    private VerifyCodeService verifyCodeService;

    @ApiOperation(value = "用户登录认证", notes = "用户名，密码登录格式 {\"username\":\"admin\",\"password\":\"admin\"}")
    @PostMapping("/login")
    public JsonResult login(@RequestBody @Validated LoginUserVO user, HttpServletRequest request, BindingResult br) {
        // 根据 CodeKey 从 redis 中获取到 ServerCodeText
        if (!verifyCodeService.checkVerifyCode(user.getCodeKey(), user.getCodeText())) {
            return JsonResult.fail("验证码错误！");
        }
        try {
            var tokenValue = userLoginService.login(user, request);
            // 登录成功后，就从 redis 中删除验证码
            verifyCodeService.deleteImageVerifyCode(user.getCodeKey());
            return JsonResult.success("登录成功", tokenValue);
        } catch (BadCredentialsException ex) {
            return JsonResult.error(OAuth2Error.USER_INVALID.getStatus(), OAuth2Error.USER_INVALID.getError());
        } catch (LockedException ex) {
            return JsonResult.error(OAuth2Error.USER_LOCKING.getStatus(), OAuth2Error.USER_LOCKING.getError());
        } catch (JwtAuthException ex) {
            return JsonResult.error(ex.getStatus(), ex.getMessage());
        } catch (Exception ex) {
            return JsonResult.fail(ex.getMessage());
        }
    }


    @ApiOperation(value = "用户退出登录", notes = "用户退出登录")
    @PostMapping("/logout")
    public JsonResult<String> logout() {
        jwtTokenService.logout();
        return JsonResult.success();
    }


    @ApiOperation(value = "刷新Token值", notes = "只需要在请求头中附带token即可")
    @GetMapping("/refresh")
    public JsonResult<JwtTokenValue> refresh() {
        var refresh = jwtTokenService.refresh();
        return JsonResult.success("刷新token成功", refresh);
    }


    @ApiOperation(value = "获取图形验证码", notes = "获取图形验证码, codeKey 前端传入一个随机生成的字符串")
    @GetMapping("/captcha/codes/{codeKey}")
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

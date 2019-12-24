package com.ifsaid.shark.controller;

import com.ifsaid.shark.common.jwt.JwtTokenUtils;
import com.ifsaid.shark.common.jwt.JwtUser;
import com.ifsaid.shark.service.SysUserService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.VerifyCodeUtils;
import com.ifsaid.shark.vo.LoginUser;
import com.ifsaid.shark.vo.TokenValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


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
    @Lazy
    private RedisTemplate<String, String> redisTemplate;


    @ApiOperation(value = "用户登录认证", notes = "用户名，密码登录格式 {\"username\":\"admin\",\"password\":\"admin\"}")
    @PostMapping("/login")
    public JsonResult<TokenValue> login(@RequestBody @Validated LoginUser user, BindingResult br) {
        if (br.hasErrors()) {
            String message = br.getFieldError().getDefaultMessage();
            return JsonResult.fail(message);
        }
        // 根据 CodeKey 从 redis 中获取到 codeText
        String codeText = redisTemplate.opsForValue().get(user.getCodeKey());
        // 比较 redis 中的 codeText 和 用户输入的 codeText
        // 将 redis.codeText 和 user.codeText 都转换成小写，然后比较
        if (StringUtils.isEmpty(codeText) || !codeText.toLowerCase().equals(user.getCodeText().toLowerCase())) {
            return JsonResult.fail("验证码错误！");
        }
        log.info("LoginUser : {}", user);
        try {
            String jwtToken = sysUserService.login(user.getUsername(), user.getPassword());
            TokenValue tokenValue = TokenValue.builder()
                    .header(jwtTokenUtils.getTokenHeader())
                    .value(jwtToken)
                    .prefix(jwtTokenUtils.getTokenHead())
                    .expiration(jwtTokenUtils.getExpiration())
                    .build();
            // 登录成功后！删除 redis 中的验证码
            redisTemplate.delete(user.getCodeKey());
            return JsonResult.success("登录成功", tokenValue);
        } catch (AuthenticationException ex) {
            return JsonResult.fail(ex.getMessage());
        }
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
        VerifyCodeUtils.ImageVerifyCode image = VerifyCodeUtils.getImage();
        // 获取 图片 验证码中的文本
        String codeText = image.getCodeText();

        // 将验证码的 codeKey 和 codeText , 保存在 redis 中，有效时间为 10 分钟
        redisTemplate.opsForValue().set(codeKey, codeText, 10, TimeUnit.MINUTES);

        ImageIO.write(image.getImage(), "JPEG", response.getOutputStream());
    }

}

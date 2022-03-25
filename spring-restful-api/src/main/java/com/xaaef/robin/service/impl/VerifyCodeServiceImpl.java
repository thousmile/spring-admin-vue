package com.xaaef.robin.service.impl;

import com.xaaef.robin.jwt.JwtTokenProperties;
import com.xaaef.robin.jwt.JwtTokenUtils;
import com.xaaef.robin.service.VerifyCodeService;
import com.xaaef.robin.util.VerifyCodeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import static com.xaaef.robin.constant.LoginConstant.CAPTCHA_CODE_KEY;


/**
 * <p>
 *
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0
 * @createTime 2020/3/5 0005 11:32
 */

@Slf4j
@Service
@AllArgsConstructor
public class VerifyCodeServiceImpl implements VerifyCodeService {

    private RedisTemplate<String, String> redisTemplate;

    private JwtTokenUtils jwtTokenUtils;

    @Override
    public BufferedImage randomImageVerifyCode(String codeKey) {
        VerifyCodeUtils.ImageVerifyCode image = VerifyCodeUtils.getImage();
        JwtTokenProperties props = jwtTokenUtils.getProps();
        // 将验证码的 codeKey 和 codeText , 保存在 redis 中，有效时间为 10 分钟
        redisTemplate.opsForValue().set(
                CAPTCHA_CODE_KEY + codeKey,
                image.getCodeText().toUpperCase(),
                props.getSmsCodeExpired(),
                TimeUnit.SECONDS
        );
        return image.getImage();
    }

    @Override
    public void deleteImageVerifyCode(String codeKey) {
        redisTemplate.delete(CAPTCHA_CODE_KEY + codeKey);
    }

    @Override
    public boolean checkVerifyCode(String codeKey, String userCodeText) {
        // 获取服务器的 CodeText
        String serverCodeText = redisTemplate.opsForValue().get(CAPTCHA_CODE_KEY + codeKey);
        // 将 serverCodeText 和 user.codeText 都转换成小写，然后比较
        return StringUtils.equals(serverCodeText, userCodeText.toUpperCase());
    }

}

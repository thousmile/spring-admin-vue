package com.ifsaid.shark.service.impl;

import com.ifsaid.shark.service.VerifyCodeService;
import com.ifsaid.shark.util.VerifyCodeUtils;
import com.ifsaid.shark.vo.ImageVerifyCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

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
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Autowired
    @Lazy
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public BufferedImage randomImageVerifyCode(String codeKey) {
        ImageVerifyCode image = VerifyCodeUtils.getImage();
        // 将验证码的 codeKey 和 codeText , 保存在 redis 中，有效时间为 10 分钟
        redisTemplate.opsForValue().set(codeKey, image.getCodeText().toUpperCase(), 10, TimeUnit.MINUTES);
        return image.getImage();
    }

    @Override
    public void deleteImageVerifyCode(String codeKey) {
        redisTemplate.delete(codeKey);
    }

    @Override
    public boolean checkVerifyCode(String codeKey, String userCodeText) {
        // 获取服务器的 CodeText
        String serverCodeText = redisTemplate.opsForValue().get(codeKey);
        // 将 serverCodeText 和 user.codeText 都转换成小写，然后比较
        if (StringUtils.isEmpty(userCodeText) || !serverCodeText.equals(userCodeText.toUpperCase())) {
            return false;
        } else {
            return true;
        }
    }

}

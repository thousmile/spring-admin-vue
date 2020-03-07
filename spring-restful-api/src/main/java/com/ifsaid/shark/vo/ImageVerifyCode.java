package com.ifsaid.shark.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.image.BufferedImage;

/**
 * <p>
 *  图形验证码
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0
 * @createTime 2020/3/5 0005 11:30
 */

@Getter
@AllArgsConstructor
public class ImageVerifyCode {

    private String codeText;

    private BufferedImage image;

}

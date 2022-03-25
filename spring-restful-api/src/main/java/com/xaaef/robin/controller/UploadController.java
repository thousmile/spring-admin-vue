package com.xaaef.robin.controller;

import com.xaaef.robin.util.JsonResult;
import com.xaaef.robin.util.QiniuUtils;
import com.xaaef.robin.util.RegexUtils;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 文件上传 控制器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */

@Slf4j
@Api(tags = "[ 七牛云 ]文件上传")
@RestController
@AllArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    private QiniuUtils qiniuUtils;

    /**
     * 图片上传
     *
     * @param file
     * @return java.io.File
     * @throws IOException
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:03
     */
    @ApiOperation(value = "图片上传", notes = "普通的图片上传：500px * 500px")
    @PostMapping("/image")
    public JsonResult uploadImages(MultipartFile file) throws IOException {
        if (file.isEmpty() || StringUtils.isEmpty(file.getOriginalFilename())) {
            return JsonResult.fail("图片不能为空");
        }
        // 校验 图片的后缀名 是否符合要求
        if (RegexUtils.isImage(file.getOriginalFilename())) {
            Map<String, String> map = uploadFile(file, 500, 500);
            return JsonResult.success(map);
        }
        return JsonResult.fail("图片格式不正确,只可以上传[ JPG , JPEG , PNG , BMP ]中的一种");
    }


    /**
     * 用户头像上传
     *
     * @param file
     * @return java.io.File
     * @throws IOException
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:03
     */
    @ApiOperation(value = "头像上传", notes = "普通的图片上传：200px * 200px")
    @PostMapping("/avatar")
    public JsonResult uploadAvatar(MultipartFile file) throws IOException {
        if (file.isEmpty() || StringUtils.isEmpty(file.getOriginalFilename())) {
            return JsonResult.fail("头像不能为空");
        }
        // 校验 图片的后缀名 是否符合要求
        if (RegexUtils.isImage(file.getOriginalFilename())) {
            Map<String, String> map = uploadFile(file, 200, 200);
            return JsonResult.success(map);
        }
        return JsonResult.fail("头像格式不正确,只可以上传[ JPG , JPEG , PNG , BMP ]中的一种");
    }


    /**
     * 删除图片
     *
     * @param url
     * @return java.io.File
     * @throws IOException
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:03
     */
    @ApiOperation(value = "图片删除", notes = "根据url 删除图片")
    @DeleteMapping("/delete")
    public JsonResult deleteImages(String url) throws IOException {
        if (StringUtils.isNotEmpty(url)) {
            String fileKey = url.substring((url.lastIndexOf("/") + 1), url.length());
            log.warn("deleteImages url: {}  fileKey: {}", url, fileKey);
            qiniuUtils.delete(fileKey);
        }
        return JsonResult.success();
    }


    /**
     * 图片压缩并且上传 七牛云 OSS
     *
     * @param file
     * @param height
     * @param width
     * @return Map<String, String>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:06
     */
    private Map<String, String> uploadFile(MultipartFile file, int width, int height) throws IOException {
        var byteArray = new ByteArrayOutputStream();

        // 因为是spring boot 打包以后是jar包，所以需要用ClassPathResource来获取classpath下面的水印图片
        InputStream watermark = new ClassPathResource("images/watermark.png").getInputStream();

        // 图片压缩以后读到空文件中
        Thumbnails.of(file.getInputStream())
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), 0.5f)
                .outputQuality(0.8f)
                .size(width, height)
                .toOutputStream(byteArray);

        // 根据文件后缀，创建一个新的文件名
        String fileName = qiniuUtils.createFileName(Objects.requireNonNull(file.getOriginalFilename()));

        // 把压缩好的图片上传到 七牛云
        String url = qiniuUtils.upload(byteArray.toByteArray(), fileName);

        log.info("uploadFile( {} , {} , {} , {} , {} );", file.getOriginalFilename(), file.getSize(), file.getContentType(), fileName, url);

        // 关闭流
        byteArray.close();

        return Map.of(
                "url", url,
                "fileName", fileName
        );
    }


}

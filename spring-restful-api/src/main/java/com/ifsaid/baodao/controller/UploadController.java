package com.ifsaid.baodao.controller;


import com.ifsaid.baodao.utils.AliyunOssUtil;
import com.ifsaid.baodao.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Wang Chen Chen
 * @Description: 文件上传
 * @Date: 16:12 2018/11/18
 */

@Slf4j
@Api(tags = "文件上传")
@RestController
@RequestMapping("/upload")
public class UploadController {

    /**
     * @Author: Wang Chen Chen
     * @Description: 图片上传
     * @Date: 16:14 2018/11/18
     * @Param: [fileName]
     * @return: java.io.File
     */
    @ApiOperation(value = "图片上传", notes = "普通的图片上传：500px * 500px")
    @PostMapping("/image")
    public Result uploadImages(MultipartFile file) throws IOException {
        if (file.isEmpty() || StringUtils.isEmpty(file.getOriginalFilename())) {
            Result.error500("图片不能为空", null);
        }
        String contentType = file.getContentType();
        if (!contentType.contains("")) {
            Result.error500("图片格式不能为空", null);
        }
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        // 判断图片的格式是否正确
        if (suffix.toUpperCase().equals("BMP") || suffix.toUpperCase().equals("JPG")
                || suffix.toUpperCase().equals("JPEG") || suffix.toUpperCase().equals("PNG")
                || suffix.toUpperCase().equals("GIF")) {
            Map<String, String> map = uploadFile(file, 500, 500);
            return Result.success(map);
        }
        return Result.error500("图片格式不正确,只可以上传[ BMP,JPG,JPEG,PNG,GIF ]中的一种", null);
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 删除图片
     * @Date: 16:14 2018/11/18
     * @Param: [fileName]
     * @return: java.io.File
     */
    @ApiOperation(value = "图片删除", notes = "根据url 删除图片")
    @DeleteMapping("/delete")
    public Result deleteImages(@RequestBody Map<String, String> map) throws IOException {
        String url = map.get("url");
        log.info("deleteImages url: {}", url);
        if (url != null && url.trim() != "") {
            AliyunOssUtil.deleteFile(url);
        }
        return Result.success();
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 头像上传
     * @Date: 16:14 2018/11/18
     * @Param: [fileName]
     * @return: java.io.File
     */
    @ApiOperation(value = "头像上传", notes = "普通的图片上传：200px * 200px")
    @PostMapping("/avatar")
    public Result uploadAvatar(MultipartFile file) throws IOException {
        if (file.isEmpty() || StringUtils.isEmpty(file.getOriginalFilename())) {
            Result.error500("头像不能为空", null);
        }
        String contentType = file.getContentType();
        if (!contentType.contains("")) {
            Result.error500("头像格式不能为空", null);
        }
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        // 判断图片的格式是否正确
        if (suffix.toUpperCase().equals("BMP") || suffix.toUpperCase().equals("JPG")
                || suffix.toUpperCase().equals("JPEG") || suffix.toUpperCase().equals("PNG")
                || suffix.toUpperCase().equals("GIF")) {
            Map<String, String> map = uploadFile(file, 200, 200);
            return Result.success(map);
        }
        return Result.error500("头像格式不正确,只可以上传[ BMP,JPG,JPEG,PNG,GIF ]中的一种", null);
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 图片压缩并且上传阿里云OSS
     * @Date: 16:17 2018/11/18
     * @Param: [file, width, height]
     * @return: java.util.Map<String,String>
     */
    public Map<String, String> uploadFile(MultipartFile file, int width, int height) throws IOException {
        // 获取一个空文件
        File nullFile = makeParentFolder(file.getOriginalFilename());
        // 因为是spring boot 打包以后是jar包，所以需要用ClassPathResource来获取classpath下面的水印图片
        InputStream watermark = new ClassPathResource("images/watermark.png").getInputStream();
        // 图片压缩以后读到空文件中
        Thumbnails.of(file.getInputStream())
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), 0.5f)
                .outputQuality(0.8f)
                .size(width, height).toFile(nullFile);
        // 把上一不压缩好的图片上传到阿里云OSS
        String url = AliyunOssUtil.putObject(new FileInputStream(nullFile), file.getContentType(), AliyunOssUtil.createFolder() + nullFile.getName());
        Map<String, String> result = new HashMap<>(2);
        result.put("url", url);
        result.put("fileName", nullFile.getName());
        log.info("uploadFile( {} , {} , {} , {} );", file.getOriginalFilename(), file.getSize(), file.getContentType(), nullFile.getName(), url);
        // 删除压缩文件
        nullFile.delete();
        return result;
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 创建文件夹
     * @Date: 16:14 2018/11/18
     * @Param: [fileName]
     * @return: java.io.File
     */
    private File makeParentFolder(String fileName) {
        // 获取文件的原名称，生成一个UUID的文件名称
        String uuidFileName = AliyunOssUtil.createFileName(fileName);
        // 构建文件路径
        StringBuilder sb = new StringBuilder(System.getProperties().getProperty("user.home"));
        sb.append("/imagesCache/").append(uuidFileName);
        log.info("生成缓存文件 地址是:  {}", sb.toString());
        // 生成文件
        File originalFile = new File(sb.toString());
        //判断文件父目录是否存在
        if (!originalFile.getParentFile().exists()) {
            // 创建文件目录
            originalFile.getParentFile().mkdir();
        }
        return originalFile;
    }


}

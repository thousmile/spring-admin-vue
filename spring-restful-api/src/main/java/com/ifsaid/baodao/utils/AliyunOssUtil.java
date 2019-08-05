package com.ifsaid.baodao.utils;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.*;

/**
 * @Author: Wang Chen Chen
 * @Description: 阿里云OSS工具类
 * @Date: 15:34 2018/11/18
 */

@Slf4j
public class AliyunOssUtil {

    static {
        // 加载阿里云oss配置文件到配置类中
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = new ClassPathResource("aliyunoss.properties").getInputStream();
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        config = new AliyunOssConfig(prop.getProperty("endpoint").trim(),
                prop.getProperty("accessKeyId").trim(),
                prop.getProperty("accessKeySecret").trim(),
                prop.getProperty("bucketName").trim()
        );
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 阿里云oss配置
     * @Date: 15:50 2018/11/18
     */
    private static AliyunOssConfig config;


    /**
     * @Author: Wang Chen Chen
     * @Description: 生成以年月日时分秒为分割的文件夹
     * @Date: 15:55 2018/11/18
     * @return: java.lang.String
     */
    public final static String createFolder() {
        Calendar now = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(now.get(Calendar.YEAR))
                .append("/")
                .append((now.get(Calendar.MONTH) + 1))
                .append("/")
                .append(now.get(Calendar.DAY_OF_MONTH))
                .append("/")
                .append(now.get(Calendar.HOUR_OF_DAY))
                .append("/")
                .append(now.get(Calendar.MINUTE))
                .append("/");
        return sb.toString();
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 根据原始文件名称，生成uuid文件名称
     * @Date: 15:58 2018/11/18
     * @Param: [originalFileName]
     * @return: java.lang.String
     */
    public final static String createFileName(String originalFileName) {
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        return UUID.randomUUID().toString().toLowerCase().replace("-", "") + "." + suffix;
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 根据文件名称生成 以年月日时分秒的uud文件名称
     * @Date: 15:58 2018/11/18
     * @Param: [originalFileName]
     * @return: java.lang.String
     */
    public final static String createFolderFileName(String originalFileName) {
        return createFolder() + createFileName(originalFileName);
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 上传文件，并且返回URL地址
     * @Date: 16:01 2018/11/18
     * @Param: [input, contentType, fileName]
     * @return: java.lang.String
     */
    public final static String putObject(InputStream input, String contentType, String fileName) {
        // 默认null
        OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        //创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 被下载时网页的缓存行为
        meta.setCacheControl("no-cache");
        meta.setHeader("Pragma", "no-cache");
        meta.setContentType(contentType);
        // 创建上传请求
        PutObjectRequest request = new PutObjectRequest(config.getBucketName(), fileName, input, meta);
        try {
            PutObjectResult putObjectResult = ossClient.putObject(request);
            log.info("putObjectResult: {}", putObjectResult);
        } finally {
            ossClient.shutdown();
        }
        // 构造URL路径
        String url = config.getEndpoint().replaceFirst("http://", "https://" + config.getBucketName() + ".") + fileName;
        return url;
    }


    /**
     * @Author: Wang Chen Chen
     * @Description: 上传文件
     * @Date: 16:03 2018/11/18
     * @Param: [file, fileType, fileName]
     * @return: java.lang.String
     */
    public final static String putObject(File file, String fileType, String fileName) throws FileNotFoundException {
        return putObject(new FileInputStream(file), fileType, fileName);
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 单文件删除
     * @Date: 16:05 2018/11/18
     * @Param: [fileUrl]
     * @return: boolean
     */
    public final static boolean deleteFile(String fileUrl) {
        int result = deleteFile(Arrays.asList(fileUrl));
        if (result > 0) {
            return true;
        }
        return false;
    }


    /**
     * @Author: Wang Chen Chen
     * @Description: 批量文件删除(较快)：适用于相同endPoint和BucketName
     * @Date: 16:09 2018/11/18
     * @Param: [fileUrls]
     * @return: int
     */
    public final static int deleteFile(List<String> fileUrls) {

        // 成功删除的个数
        int deleteCount = 0;
        // 根据url获取bucketName
        String bucketName = getBucketName(fileUrls.get(0));
        // 根据url获取fileName
        List<String> fileNames = getFileName(fileUrls);
        if (bucketName == null || fileNames.size() <= 0)
            return 0;
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(fileNames);
            DeleteObjectsResult result = ossClient.deleteObjects(request);
            deleteCount = result.getDeletedObjects().size();
        } catch (OSSException oe) {
            oe.printStackTrace();
            throw new RuntimeException("OSS服务异常:", oe);
        } catch (ClientException ce) {
            ce.printStackTrace();
            throw new RuntimeException("OSS客户端异常:", ce);
        } finally {
            ossClient.shutdown();
        }
        return deleteCount;
    }

    /**
     * @param fileUrls 需要删除的文件url集合
     * @return int 成功删除的个数
     * @MethodName: batchDeleteFiles
     * @Description: 批量文件删除(较慢)：适用于不同endPoint和BucketName
     */
    public final static int deleteFiles(List<String> fileUrls) {
        int count = 0;
        for (String url : fileUrls) {
            if (deleteFile(url)) {
                count++;
            }
        }
        return count;
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 获取文件类型
     * @Date: 16:08 2018/11/18
     * @Param: [fileType]
     * @return: java.lang.String
     */
    public final static String contentType(String fileType) {

        fileType = fileType.toLowerCase();
        String contentType = "";
        switch (fileType) {
            case "bmp":
                contentType = "image/bmp";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            case "png":
            case "jpeg":
            case "jpg":
                contentType = "image/jpeg";
                break;
            case "html":
                contentType = "text/html";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "vsd":
                contentType = "application/vnd.visio";
                break;
            case "ppt":
            case "pptx":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "doc":
            case "docx":
                contentType = "application/msword";
                break;
            case "xml":
                contentType = "text/xml";
                break;
            case "mp4":
                contentType = "video/mp4";
                break;
            default:
                contentType = "application/octet-stream";
                break;
        }
        return contentType;
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 根据url获取bucketName
     * @Date: 16:07 2018/11/18
     * @Param: [fileUrl]
     * @return: java.lang.String
     */
    public final static String getBucketName(String fileUrl) {

        String http = "http://";
        String https = "https://";
        int httpIndex = fileUrl.indexOf(http);
        int httpsIndex = fileUrl.indexOf(https);
        int startIndex = 0;
        if (httpIndex == -1) {
            if (httpsIndex == -1) {
                return null;
            } else {
                startIndex = httpsIndex + https.length();
            }
        } else {
            startIndex = httpIndex + http.length();
        }
        int endIndex = fileUrl.indexOf(".oss-");
        return fileUrl.substring(startIndex, endIndex);
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 根据url获取fileName
     * @Date: 16:07 2018/11/18
     * @Param: [fileUrls]
     * @return: java.util.List<java.lang.String>
     */
    public final static String getFileName(String fileUrl) {
        String str = "aliyuncs.com/";
        int beginIndex = fileUrl.indexOf(str);
        if (beginIndex == -1)
            return null;
        return fileUrl.substring(beginIndex + str.length());
    }

    /**
     * @Author: Wang Chen Chen
     * @Description: 根据url获取fileNames集合
     * @Date: 16:07 2018/11/18
     * @Param: [fileUrls]
     * @return: java.util.List<java.lang.String>
     */
    public final static List<String> getFileName(List<String> fileUrls) {
        List<String> names = new ArrayList<>();
        for (String url : fileUrls) {
            names.add(getFileName(url));
        }
        return names;
    }


}


/**
 * @Author: Wang Chen Chen
 * @Description: 阿里云OSS配置类
 * @Date: 15:35 2018/11/18
 */

@Getter
@Setter
class AliyunOssConfig {

    /**
     * @Author: Wang Chen Chen
     * @Description: 连接区域地址
     */
    private String endpoint;

    /**
     * @Author: Wang Chen Chen
     * @Description: 连接keyId
     */
    private String accessKeyId;

    /**
     * @Author: Wang Chen Chen
     * @Description: 连接秘钥
     */
    private String accessKeySecret;

    /**
     * @Author: Wang Chen Chen
     * @Description: 需要存储的bucketName
     */
    private String bucketName;

    public AliyunOssConfig(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
    }

}
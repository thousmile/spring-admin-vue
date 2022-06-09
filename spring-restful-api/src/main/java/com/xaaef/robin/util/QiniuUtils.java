package com.xaaef.robin.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 七牛云，文件上传工具类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */

@Slf4j
@Getter
@Setter
@Component
public class QiniuUtils {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucketName}")
    private String bucketName;

    @Value("${qiniu.fileDomain}")
    private String fileDomain;

    private UploadManager uploadManager;

    private BucketManager bucketManager;

    private Configuration c;

    private Client client;

    private Auth auth;

    public Client getClient() {
        if (client == null) {
            client = new Client(getConfiguration());
        }
        return client;
    }

    public BucketManager getBucketManager() {
        if (bucketManager == null) {
            bucketManager = new BucketManager(getAuth(), getConfiguration());
        }
        return bucketManager;
    }

    public UploadManager getUploadManager() {
        if (uploadManager == null) {
            uploadManager = new UploadManager(getConfiguration());
        }
        return uploadManager;
    }

    public Configuration getConfiguration() {
        if (c == null) {
            c = new Configuration(Region.autoRegion());
        }
        return c;
    }

    public Auth getAuth() {
        if (auth == null) {
            auth = Auth.create(getAccessKey(), getSecretKey());
        }
        return auth;
    }

    /**
     * 简单上传模式的凭证
     *
     * @date 2019/12/11 20:53
     */
    public String getUpToken() {
        return getAuth().uploadToken(getBucketName());
    }

    /**
     * 覆盖上传模式的凭证
     *
     * @date 2019/12/11 20:53
     */
    public String getUpToken(String fileKey) {
        return getAuth().uploadToken(getBucketName(), fileKey);
    }

    /**
     * 将本地文件上传
     *
     * @param filePath 本地文件路径
     * @param fileKey  上传到七牛后保存的文件路径名称
     * @return String
     * @throws IOException
     */
    public String upload(String filePath, String fileKey) throws IOException {
        Response res;
        try {
            res = getUploadManager().put(filePath, fileKey, getUpToken(fileKey));
            DefaultPutRet putRet = JsonUtils.toPojo(res.body(), DefaultPutRet.class);
            assert putRet != null;
            return fileDomain + "/" + putRet.key;
        } catch (QiniuException e) {
            res = e.response;
            log.error(e.getMessage());
            return "上传失败";
        }
    }

    /**
     * 上传二进制数据，返回 文件的 UIR
     *
     * @param data
     * @param fileKey
     * @return String
     * @throws IOException
     */
    public String upload(byte[] data, String fileKey) throws IOException {
        Response res;
        try {
            res = getUploadManager().put(data, fileKey, getUpToken(fileKey));
            DefaultPutRet putRet = JsonUtils.toPojo(res.body(), DefaultPutRet.class);
            assert putRet != null;
            return fileDomain + "/" + putRet.key;
        } catch (QiniuException e) {
            res = e.response;
            e.printStackTrace();
            return "上传失败";
        }
    }

    /**
     * 上传输入流，返回 文件的 UIR
     *
     * @param inputStream
     * @param fileKey
     * @return String
     * @throws IOException
     */
    public String upload(InputStream inputStream, String fileKey) throws IOException {
        Response res;
        try {
            res = getUploadManager().put(inputStream, fileKey, getUpToken(fileKey), null, null);
            DefaultPutRet putRet = JsonUtils.toPojo(res.body(), DefaultPutRet.class);
            assert putRet != null;
            return fileDomain + "/" + putRet.key;
        } catch (QiniuException e) {
            res = e.response;
            e.printStackTrace();
            return "上传失败";
        }
    }

    /**
     * 删除文件
     *
     * @param fileKey
     * @return boolean
     * @throws QiniuException
     */
    public boolean delete(String fileKey) throws QiniuException {
        Response response = getBucketManager().delete(this.getBucketName(), fileKey);
        return response.statusCode == HttpStatus.OK.value();
    }

    /**
     * 获取公共空间文件
     *
     * @param fileKey
     * @return String
     * @throws Exception
     */
    public String getFile(String fileKey) throws Exception {
        String encodedFileName = URLEncoder.encode(fileKey, StandardCharsets.UTF_8).replace("+", "%20");
        return String.format("%s/%s", fileDomain, encodedFileName);
    }

    /**
     * 获取私有空间文件
     *
     * @param fileKey
     * @return String
     * @throws Exception
     */
    public String getPrivateFile(String fileKey) throws Exception {
        String encodedFileName = URLEncoder.encode(fileKey, StandardCharsets.UTF_8).replace("+", "%20");
        String url = String.format("%s/%s", fileDomain, encodedFileName);
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;
        //1小时，可以自定义链接过期时间
        return auth.privateDownloadUrl(url, expireInSeconds);
    }


    /**
     * 生成以  /年/月/日/时/分/秒/ 为分割的文件夹
     *
     * @return String
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:09
     */
    public String createFolder() {
        LocalDateTime dateTime = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append(dateTime.getYear())
                .append("/")
                .append(dateTime.getMonthValue())
                .append("/")
                .append(dateTime.getDayOfMonth())
                .append("/")
                .append(dateTime.getHour())
                .append("/")
                .append(dateTime.getMinute())
                .append("/");
        return sb.toString();
    }

    /**
     * 根据原始文件名称，生成uuid文件名称
     *
     * @return java.lang.String
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:10
     */
    public String createFileName(String originalFileName) {
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        return IdUtils.getSimpleStrId() + "." + suffix;
    }

}

package com.gi.ctdn.cloud.storage.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AliOSSUtil {

    private final static Logger log = Logger.getLogger(AliOSSUtil.class);
    @Value("${OSS_ENDPOINT}")
    private  String OSS_ENDPOINT;
    @Value("${OSS_ACCESS_KEY_ID}")
    private  String OSS_ACCESS_KEY_ID;
    @Value("${OSS_ACCESS_KEY_SECRET}")
    private  String OSS_ACCESS_KEY_SECRET;
    @Value("${OSS_BUCKET_NAME}")
    private  String OSS_BUCKET_NAME;
    // 文件访问域名
    private static String FILE_HOST;

    /**
     *
     * @param inputStream
     * @return
     * @throws FileNotFoundException
     */
    public Map<String,String> upload(InputStream inputStream, String filename, boolean isurl) {
        if (inputStream == null) {
            return null;
        }
        Map<String,String> resultMap = new HashMap<>();
        // 创建OSS客户端
        OSSClient ossClient = new OSSClient(OSS_ENDPOINT, OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET);
        try {
            // 判断文件容器是否存在，不存在则创建
            if (!ossClient.doesBucketExist(OSS_BUCKET_NAME)) {
                ossClient.createBucket(OSS_BUCKET_NAME);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(OSS_BUCKET_NAME);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(OSS_BUCKET_NAME, filename, inputStream));
            resultMap.put("etag",result.getETag());
            if (isurl){
                Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
                URL url = ossClient.generatePresignedUrl(OSS_BUCKET_NAME, filename, expiration);
                resultMap.put("url",url.toString());
            }
            return resultMap;

        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return null;

    }

    public void setOSS_ENDPOINT(String OSS_ENDPOINT) {
        this.OSS_ENDPOINT = OSS_ENDPOINT;
    }

    public void setOSS_ACCESS_KEY_ID(String OSS_ACCESS_KEY_ID) {
        this.OSS_ACCESS_KEY_ID = OSS_ACCESS_KEY_ID;
    }

    public void setOSS_ACCESS_KEY_SECRET(String OSS_ACCESS_KEY_SECRET) {
        this.OSS_ACCESS_KEY_SECRET = OSS_ACCESS_KEY_SECRET;
    }

    public void setOSS_BUCKET_NAME(String OSS_BUCKET_NAME) {
        this.OSS_BUCKET_NAME = OSS_BUCKET_NAME;
    }


}
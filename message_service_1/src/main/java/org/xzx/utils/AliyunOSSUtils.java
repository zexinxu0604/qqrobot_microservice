package org.xzx.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Component;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ResponseHeaderOverrides;

import java.net.URL;
import java.util.Date;

@Component
@Log4j2
public class AliyunOSSUtils {
    @Autowired
    @Qualifier("AliyunOSSClient")
    private OSS ossClient;

    @Value("${aliyun.oss.jx3_image_bucket}")
    private String bucket;

    public String getImageUrl(String url) {
        try {
            Date expiration = new Date(new Date().getTime() + 3600 * 1000L);

            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, url, HttpMethod.GET);
            req.setExpiration(expiration);

            ResponseHeaderOverrides responseHeaderOverrides = new ResponseHeaderOverrides();
            req.setResponseHeaders(responseHeaderOverrides);

            URL oss_url = ossClient.generatePresignedUrl(req);
            log.info("从阿里云获取图片url:" + oss_url.toString());
            return oss_url.toString();
        } catch (OSSException oe){
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());
            return null;
        } catch (ClientException ce){
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());
            return null;
        }
    }

    public boolean deleteFromOSS(String filename){
        try {
            ossClient.deleteObject(bucket, filename);
            return true;
        } catch (OSSException oe){
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());
            return false;
        } catch (ClientException ce){
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());
            return false;
        }
    }

    public MediaType getImageContentType(String url){
        if(url.endsWith(".png")) return MediaType.IMAGE_PNG;
        else if(url.endsWith(".jpg") || url.endsWith(".jpeg")) return MediaType.IMAGE_JPEG;
        else if(url.endsWith(".gif")) return MediaType.IMAGE_GIF;
        else return MediaType.IMAGE_JPEG;
    }
}

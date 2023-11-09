package org.xzx.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
            URL oss_url = ossClient.generatePresignedUrl(bucket, url, expiration);
            log.info("从阿里云获取图片url:" + url);
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
}

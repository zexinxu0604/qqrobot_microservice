package org.xzx.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;
import org.xzx.bean.response.QQimageCheckUrlResponse;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
@Log4j2
public class Url_utils {

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("AliyunOSSClient")
    private OSS ossClient;

    @Value("${aliyun.oss.jx3_image_bucket}")
    private String bucket;

    /**
     * 检查图片的url是否可用，qq的图片url有时效性，所以需要检查
     *
     * @param url 图片的url
     * @return 是否可用
     */
    public boolean checkUrl(String url) {
        try {
            QQimageCheckUrlResponse qqimageCheckUrlResponse = restTemplate.getForObject(url, QQimageCheckUrlResponse.class);
            if (qqimageCheckUrlResponse != null) {
                return false;
            }
            return false;
        } catch (UnknownContentTypeException e) {
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

    /**
     * 下载并获取本地图片名称
     *
     * @param url  url
     * @param path 文件路径
     * @return 带格式的文件名
     */
    public String downloadImage(String url, String path, String imageName) {
        try {
            //用restTemplate下载图片
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
            //获取entity中的数据
            byte[] body = responseEntity.getBody();
            //创建输出流  输出到本地
            if (body != null) {
                InputStream inputStream = new ByteArrayInputStream(body);
                String format = getWebImageFormat(inputStream);
                FileOutputStream fileOutputStream = new FileOutputStream(path + "." + format);
                fileOutputStream.write(body);
                //关闭流
                fileOutputStream.close();
                return imageName + "." + format;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 下载并获取本地图片名称
     *
     * @param url url
     * @return 带格式的文件名
     */
    public String sendImageToOSS(String url, String imageName) {
        try {
            //用restTemplate下载图片
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
            //获取entity中的数据
            byte[] body = responseEntity.getBody();
            //创建输出流  输出到本地
            if (body != null) {
                InputStream inputStream = new ByteArrayInputStream(body);
                String format = getWebImageFormat(inputStream);
                format = format.toLowerCase();

                InputStream inputStream2 = new URL(url).openStream();
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, imageName + "." + format, inputStream2);
                PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
                return imageName + "." + format;
            }
            log.error("下载图片时出现问题");
            return null;
        } catch (IOException e) {
            log.error("下载图片时出现问题", e);
            return null;
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());
            return null;
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());
            return null;
        }
    }

    public String getWebImageFormat(InputStream srcInputStream) throws IOException {
        // 获取ImageInputStream 对象
        try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(srcInputStream)) {
            ImageReader reader = ImageIO.getImageReaders(imageInputStream).next();
            reader.setInput(imageInputStream);
            return reader.getFormatName();
        }
    }
}

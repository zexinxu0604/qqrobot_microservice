package org.xzx.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;

@Component
public class Url_utils {

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;
    /**
     * 检查图片的url是否可用，qq的图片url有时效性，所以需要检查
     * @param url 图片的url
     * @return 是否可用
     */
    public boolean checkUrl(String url){
        try {
            restTemplate.getForObject(url, String.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 下载并获取本地图片名称
     * @param url url
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
            if(body != null){
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

    public String getWebImageFormat(InputStream srcInputStream) throws IOException {
        // 获取ImageInputStream 对象
        try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(srcInputStream)) {
            ImageReader reader = ImageIO.getImageReaders(imageInputStream).next();
            reader.setInput(imageInputStream);
            return reader.getFormatName();
        }
    }
}

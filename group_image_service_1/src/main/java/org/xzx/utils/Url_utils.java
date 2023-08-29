package org.xzx.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public boolean downloadImage(String url, String path) {
        try {
            //用restTemplate下载图片
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
            //获取entity中的数据
            byte[] body = responseEntity.getBody();
            //创建输出流  输出到本地
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            fileOutputStream.write(body);
            //关闭流
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

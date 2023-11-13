package org.xzx.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.xzx.bean.qqGroupBean.GroupInfo;

import java.util.List;
import java.util.Arrays;

@Service
@Log4j2
public class SpringRestService {
    @Autowired
    private RestTemplate restTemplate;

    public <T> T postWithObject(String url, Object param, Class<T> responseType) {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        // 设置ContentType
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 请求
        return postWithObject(url, headers, param, responseType);
    }

    public <T> T postWithObject(String url, HttpHeaders headers, Object param, Class<T> responseType) {
        // 设置ContentType
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(param, headers);
        // post请求并返回
        return restTemplate.postForObject(url, request, responseType);
    }

    public <T> T postWithJson(String url, JsonNode param, Class<T> responseType) {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        // 设置ContentType
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 请求
        return postWithJson(url, headers, param, responseType);
    }

    public <T> T postWithJson(String url, HttpHeaders headers, JsonNode param, Class<T> responseType) {
        // 请求
        return postWithJson(url, headers, param.asText(), responseType);
    }


    public <T> T postWithJson(String url, HttpHeaders headers, String param, Class<T> responseType) {
        // 设置ContentType
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 请求中设置param和headers
        HttpEntity<String> request = new HttpEntity<>(param, headers);
        // post请求并返回
        return restTemplate.postForObject(url, request, responseType);
    }


    public <T> T getForObject(String url, Class<T> responseType) {
        // get请求并返回
        return restTemplate.getForObject(url, responseType);
    }


    public String getForObject(String url, HttpHeaders headers) {
        // 设置ContentType
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 设置请求头
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        // get请求获取响应内容
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        // 返回响应body
        return response.getBody();
    }


    public String getForXml(String url, HttpHeaders headers) {
        // 设置ACCEPT
        headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
        // 设置ContentType
        headers.setContentType(MediaType.APPLICATION_XML);
        // 设置请求头
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        // get请求获取响应内容
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        // 返回响应body
        return response.getBody();
    }

    public <T> List<T> getForList(String url, Class<T> responseType) {
        System.out.println("准备发送");

        ResponseEntity<List<T>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<T>>(){});
        System.out.println("发送http请求完成");
        return response.getBody();
    }

    public List<GroupInfo> getGroupList(String url){
        ResponseEntity<List<GroupInfo>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GroupInfo>>(){});
        System.out.println("发送http请求完成");
        return response.getBody();
    }

}

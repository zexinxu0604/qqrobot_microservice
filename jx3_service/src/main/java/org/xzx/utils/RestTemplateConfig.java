package org.xzx.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class RestTemplateConfig {

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "restTemplateWithProxy")
    public RestTemplate restTemplateWithProxy() {
        final String PROXY_SERVER_HOST = "127.0.0.1";
        final int PROXY_SERVER_PORT = 8118;
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_SERVER_HOST, PROXY_SERVER_PORT));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);
        return new RestTemplate(requestFactory);
    }

}

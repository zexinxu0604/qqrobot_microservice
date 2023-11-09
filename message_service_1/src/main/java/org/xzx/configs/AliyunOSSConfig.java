package org.xzx.configs;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliyunOSSConfig {

    @Value("${aliyun.oss.accesskey}")
    private String accesskey;

    @Value("${aliyun.oss.accessSecret}")
    private String accessSecret;


    @Bean("AliyunOSSClient")
    public OSS ossClient() {
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accesskey, accessSecret);
        return new OSSClientBuilder().build(endpoint, credentialsProvider);
    }
}

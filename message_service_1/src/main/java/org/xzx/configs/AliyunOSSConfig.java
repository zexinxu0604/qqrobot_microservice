package org.xzx.configs;

import com.aliyun.oss.ClientBuilderConfiguration;
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
        String domain = "zzgroupimages.cn";
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accesskey, accessSecret);
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
// 设置是否支持CNAME。CNAME用于将自定义域名绑定到目标Bucket。
        conf.setSupportCname(true);
        return new OSSClientBuilder().build(domain, credentialsProvider, conf);
    }
}

package org.xzx.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("group-image-service")
public interface ImageClient {
    @RequestMapping("/getRandomImage")
    String getRandomImage();
}

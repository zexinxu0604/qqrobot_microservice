package org.xzx.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("group-image-service")
public interface ImageClient {
    @RequestMapping("/getRandomImage")
    String getRandomImage();

    @GetMapping("/image/checkUrl")
    boolean checkUrl(@RequestParam String url);
}

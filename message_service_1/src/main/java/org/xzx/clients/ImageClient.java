package org.xzx.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.xzx.pojo.Image.ImageResponse;

@FeignClient(name = "group-image-service", fallback = ImageClientFallBack.class)
public interface ImageClient {
    @RequestMapping("/getRandomImage")
    ImageResponse getRandomImage();

    @GetMapping("/image/checkUrl")
    int checkUrl(@RequestParam String url);

    @GetMapping("/image/insertImage")
    boolean insertImage(@RequestParam String url);

    @GetMapping("/image/deleteImage")
    boolean deleteImage(@RequestParam String url);

    @GetMapping("/image/restoreImage")
    boolean restoreImage(@RequestParam String url);
}

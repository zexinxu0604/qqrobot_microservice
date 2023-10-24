package org.xzx.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xzx.bean.Image.ImageResponse;

@FeignClient(name = "group-image-service", fallback = ImageClientFallBack.class)
public interface ImageClient {
    @RequestMapping("/getRandomImage")
    ImageResponse getRandomImage();

    @GetMapping("/image/checkUrl")
    int checkUrl(@RequestParam String url, @RequestParam long poster, @RequestParam long groupid);

    @GetMapping("/image/insertImage")
    boolean insertImage(@RequestParam String url, @RequestParam long poster, @RequestParam long groupid);

    @GetMapping("/image/deleteImage")
    boolean deleteImage(@RequestParam String url);

    @GetMapping("/image/restoreImage")
    boolean restoreImage(@RequestParam String url);
}

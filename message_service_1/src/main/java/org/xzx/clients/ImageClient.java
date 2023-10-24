package org.xzx.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.xzx.bean.response.*;

@FeignClient(name = "group-image-service", fallback = ImageClientFallBack.class)
public interface ImageClient {
    @RequestMapping("/getRandomImage")
    ImageResponse getRandomImage();

    @GetMapping("/image/checkUrl")
    ApiResponse<CheckImageResponse> checkUrl(@RequestParam String url, @RequestParam long poster, @RequestParam long groupid);

    @GetMapping("/image/insertImage")
    ApiResponse<CheckImageResponse> insertImage(@RequestParam String url, @RequestParam long poster, @RequestParam long groupid);

    @GetMapping("/image/deleteImage")
    ApiResponse<DeleteImageResponse> deleteImage(@RequestParam String url);

    @GetMapping("/image/restoreImage")
    ApiResponse<RestoreImageResponse> restoreImage(@RequestParam String url);
}

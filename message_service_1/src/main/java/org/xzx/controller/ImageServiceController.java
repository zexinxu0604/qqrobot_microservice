package org.xzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xzx.bean.response.*;
import org.xzx.service.GroupImageService;
import org.xzx.utils.Url_utils;

@Controller
public class ImageServiceController {

    @Autowired
    private GroupImageService groupImageService;

    @Autowired
    private Url_utils url_utils;


    @RequestMapping("/getRandomImage")
    @ResponseBody
    public ImageResponse getRandomImage() {
        return groupImageService.getRandomImage();
    }

    /**
     * 检查是否存在该图片，如果不存在则下载
     *
     * @param url
     * @return 0(已存在) 1(下载成功) 2(下载失败)
     */
    @GetMapping("/image/checkUrl")
    @ResponseBody
    public ApiResponse<CheckImageResponse> checkUrl(String url, long poster, long groupid) {
        return groupImageService.checkUrl(url, poster, groupid);
    }

    @GetMapping("/image/insertImage")
    @ResponseBody
    public ApiResponse<CheckImageResponse> insertImage(String url, long poster, long groupid) {
        return groupImageService.insertImage(url, poster, groupid);
    }

    @GetMapping("/image/deleteImage")
    @ResponseBody
    public ApiResponse<DeleteImageResponse> deleteImage(String url) {
        return groupImageService.deleteImage(url);
    }

    @GetMapping("/image/restoreImage")
    @ResponseBody
    public ApiResponse<RestoreImageResponse> restoreImage(String url) {
        return groupImageService.restoreImage(url);
    }
}

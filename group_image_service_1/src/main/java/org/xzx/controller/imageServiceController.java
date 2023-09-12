package org.xzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xzx.pojo.GroupImage;
import org.xzx.pojo.ImageResponse;
import org.xzx.service.GroupImageService;
import org.xzx.utils.String_Utils;
import org.xzx.utils.Url_utils;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class imageServiceController {

    @Autowired
    private GroupImageService groupImageService;

    @Autowired
    private Url_utils url_utils;

    @Value("${qq.group.imagepath}")
    private String imagepath;

    @RequestMapping("/getRandomImage")
    @ResponseBody
    public ImageResponse getRandomImage() {
        GroupImage groupImage = groupImageService.getRandomImage();
        String url = groupImage.getUrl();
        if (url_utils.checkUrl(url)) {
            return new ImageResponse(200, 0, url);
        } else {
            Path path = Paths.get(imagepath + groupImage.getLocalurl() + ".png");
            if (path.toFile().exists()) {
                return new ImageResponse(200, 1, groupImage.getLocalurl());
            } else {
                groupImageService.deleteImage(url);
                return new ImageResponse(404, 0, null);
            }
        }
    }

    /**
     * 检查是否存在该图片，如果不存在则下载
     * @param url
     * @return 0(已存在) 1(下载成功) 2(下载失败)
     */
    @GetMapping("/image/checkUrl")
    @ResponseBody
    public int checkUrl(String url) {
        String imageName = String_Utils.getImageName(url);
        if (groupImageService.isImageExist(imageName)) {
            return 0;
        } else {
            groupImageService.insertImage(url);
            System.out.println("开始下载图片");
            return url_utils.downloadImage(url, imagepath + imageName + ".png") ? 1 : 2;
        }
    }

    @GetMapping("/image/insertImage")
    @ResponseBody
    public boolean insertImage(String url) {
        return groupImageService.insertImage(url);
    }

    @GetMapping("/image/deleteImage")
    @ResponseBody
    public boolean deleteImage(String url) {
        return groupImageService.deleteImage(url);
    }
}

package org.xzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xzx.service.GroupImageService;
import org.xzx.utils.String_Utils;
@Controller
public class imageServiceController {

    @Autowired
    private GroupImageService groupImageService;

    @RequestMapping("/getRandomImage")
    @ResponseBody
    public String getRandomImage(){
        return groupImageService.getRandomImage().getUrl();
    }

    @GetMapping("/image/checkUrl")
    @ResponseBody
    public boolean checkUrl(String url){
        String imageName = String_Utils.getImageName(url);
        System.out.println(imageName);
        return groupImageService.isImageExist(imageName);
    }

    @GetMapping("/image/insertImage")
    @ResponseBody
    public boolean insertImage(String url){
        return groupImageService.insertImage(url);
    }
}

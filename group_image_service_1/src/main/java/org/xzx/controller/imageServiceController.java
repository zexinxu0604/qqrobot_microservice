package org.xzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xzx.service.GroupImageService;

@RestController
public class imageServiceController {

    @Autowired
    private GroupImageService groupImageService;

    @RequestMapping("/getRandomImage")
    @ResponseBody
    public String getRandomImage(){
        return groupImageService.getRandomImage().getUrl();
    }
}

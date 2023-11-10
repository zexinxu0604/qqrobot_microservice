package org.xzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
public class jx3ServiceController {

    @RequestMapping("/getbaizhan")
    @ResponseBody
    public String getBaizhan(){
        return null;
    }
}

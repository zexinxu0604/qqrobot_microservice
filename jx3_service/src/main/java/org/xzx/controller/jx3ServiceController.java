package org.xzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xzx.service.Playwright_service;

import java.util.concurrent.Future;

@RestController
public class jx3ServiceController {
    @Autowired
    private Playwright_service playwrightService;

    @RequestMapping("/getbaizhan")
    @ResponseBody
    public String getBaizhan(){
        Future<String> result = playwrightService.getBaiZhan();
        while (true){
            if(result.isDone()){
                try {
                    return "success";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

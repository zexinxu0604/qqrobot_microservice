package org.xzx.plugins;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.clients.ImageClient;
import org.xzx.clients.Jx3Clients;
import org.xzx.pojo.messageBean.Received_Group_Message;
import org.xzx.service.Gocq_service;
import org.xzx.utils.String_Utils;

import java.util.List;

@RobotListener
public class GroupMessageListener {

    @Resource
    private ImageClient imageClient;

    @Resource
    private Jx3Clients jx3Clients;

    @Autowired
    private Gocq_service gocqService;

    @RobotListenerHandler(order = 1)
    public void hello(Received_Group_Message message){
        if(message.getRaw_message().equals("你好")){
            System.out.println("hello");
        }
    }

    @RobotListenerHandler(order = 1)
    public void hello1(Received_Group_Message message){
        if(message.getRaw_message().equals("你好")){
            System.out.println("hello2");
        }
    }

    @RobotListenerHandler
    public void random(Received_Group_Message message){
        if(message.getRaw_message().equals("随机图片")){
            System.out.println(imageClient.getRandomImage());
        }
    }

    @RobotListenerHandler(isBlock = true, concurrency = true)
    public void getBaiZhan(Received_Group_Message message){
        if(message.getRaw_message().equals("百战")){
            if(jx3Clients.getBaizhan().equals("success")){
                gocqService.send_group_message(message.getGroup_id(), String_Utils.getImageString("baizhan.png"));
            }
        }
    }

    @RobotListenerHandler(order = 0)
    public void checkImage(Received_Group_Message receivedGroupMessage){
        if(receivedGroupMessage.getRaw_message().startsWith("[CQ:image,")){
            List<String> cqStrings = String_Utils.getCQStrings(receivedGroupMessage.getRaw_message());
            String imagecq = cqStrings.get(0);
            if(String_Utils.getImageSubType(imagecq).equals("1")){
                String image = String_Utils.getImageURL(imagecq);
                System.out.println("该image:" + imageClient.checkUrl(image));
            }
        }
    }



}

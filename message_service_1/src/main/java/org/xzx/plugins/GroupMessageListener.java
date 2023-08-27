package org.xzx.plugins;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.clients.ImageClient;
import org.xzx.pojo.messageBean.Received_Group_Message;

@RobotListener
public class GroupMessageListener {

    @Resource
    private ImageClient imageClient;
    @RobotListenerHandler(order = 0)
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
}

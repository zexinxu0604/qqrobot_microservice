package org.xzx.plugins;

import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.pojo.messageBean.Received_Group_Message;

@RobotListener
public class GroupMessageListener {
    @RobotListenerHandler
    public void hello(Received_Group_Message message){
        if(message.getRaw_message().equals("你好")){
            System.out.println("hello");
        }
    }
}

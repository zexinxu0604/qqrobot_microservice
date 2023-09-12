package org.xzx.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xzx.pojo.messageBean.Send_Message.Group_Message;
import org.xzx.pojo.messageBean.Send_Message.PrivateMessage;

@Service
public class Gocq_service {

    @Autowired
    private RestTemplate restTemplate;
    public void send_group_message(int group_id, String message){
        Group_Message groupMessage = new Group_Message(group_id, message);
        System.out.println(restTemplate.postForObject("http://127.0.0.1:5700/send_group_msg", groupMessage, String.class));
    }

    public void send_private_message(int user_id, String message) {
        PrivateMessage privateMessage = new PrivateMessage(user_id, message);
        System.out.println(restTemplate.postForObject("http://127.0.0.1:5700/send_private_msg", privateMessage, String.class));
    }

    public JsonNode get_message(int message_id){
        return restTemplate.getForObject("http://127.0.0.1:5700/get_msg?message_id=" + message_id, JsonNode.class);
    }
}

package org.xzx.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xzx.pojo.messageBean.Send_Message.SendGroupMessage;
import org.xzx.pojo.messageBean.Send_Message.PrivateMessage;

@Service
public class Gocq_service {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gocq.basicUrl}")
    private String basicUrl;

    public void send_group_message(int group_id, String message){
        SendGroupMessage groupMessage = new SendGroupMessage(group_id, message);
        System.out.println(restTemplate.postForObject(basicUrl + "send_group_msg", groupMessage, String.class));
    }

    public void send_private_message(int user_id, String message) {
        PrivateMessage privateMessage = new PrivateMessage(user_id, message);
        System.out.println(restTemplate.postForObject(basicUrl +"send_private_msg", privateMessage, String.class));
    }

    public JsonNode get_message(int message_id){
        return restTemplate.getForObject(basicUrl + "get_msg?message_id=" + message_id, JsonNode.class);
    }
}

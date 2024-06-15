package org.xzx.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.xzx.bean.messageBean.Send_Message.SendGroupMessage;
import org.xzx.bean.messageBean.Send_Message.PrivateMessage;
import org.xzx.bean.qqGroupBean.GroupInfo;

import java.util.List;

@Service
@RefreshScope
public class Gocq_service {

    @Autowired
    private SpringRestService springRestService;

    @Value("${gocq.basicUrl}")
    private String basicUrl;

    public void send_group_message(long group_id, String message) {
        SendGroupMessage groupMessage = new SendGroupMessage(group_id, message);
        System.out.println(springRestService.postWithObject(basicUrl + "send_group_msg", groupMessage, String.class));
    }

    public void send_private_message(long user_id, String message) {
        PrivateMessage privateMessage = new PrivateMessage(user_id, message);
        System.out.println(springRestService.postWithObject(basicUrl + "send_private_msg", privateMessage, String.class));
    }

    public String get_message(long message_id) {
        JsonNode jsonNode = springRestService.getForObject(basicUrl + "get_msg?message_id=" + message_id, JsonNode.class);
        return jsonNode.get("data").get("message").asText();
    }

    public List<GroupInfo> get_group_list() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = springRestService.getForObject(basicUrl + "get_group_list", JsonNode.class);
        return objectMapper.convertValue(jsonNode.get("data"), new TypeReference<List<GroupInfo>>(){});
    }
}

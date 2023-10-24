package org.xzx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xzx.handler.HandlerResolver;
import org.xzx.bean.messageBean.ReceivedGroupMessage;
import org.xzx.bean.messageBean.ReceivedPrivateMessage;

@Controller
public class MessageController {

    @RequestMapping("/message")
    public String test(@RequestBody JsonNode jsonNode, HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(jsonNode);
        if (jsonNode.get("post_type").asText().equals("message")) {
            if (jsonNode.get("message_type").asText().equals("group")) {
                ReceivedGroupMessage groupMessage = mapper.treeToValue(jsonNode, ReceivedGroupMessage.class);
                request.setAttribute("message", groupMessage);
                return "forward:/groupMessage";
            }

            if (jsonNode.get("message_type").asText().equals("private")) {
                ReceivedPrivateMessage privateMessage = mapper.treeToValue(jsonNode, ReceivedPrivateMessage.class);
                request.setAttribute("message", privateMessage);
                return "forward:/privateMessage";
            }

        }
        return "";
    }

    @RequestMapping("/groupMessage")
    @ResponseBody
    public void handler_group_message(HttpServletRequest request) {
        ReceivedGroupMessage groupMessage = (ReceivedGroupMessage) request.getAttribute("message");
        HandlerResolver.handleEvent(groupMessage);
        System.out.println(groupMessage.getMessage());
    }

    @RequestMapping("/privateMessage")
    @ResponseBody
    public void handler_private_message(HttpServletRequest request) {
        ReceivedPrivateMessage receivedPrivateMessage = (ReceivedPrivateMessage) request.getAttribute("message");
        HandlerResolver.handleEvent(receivedPrivateMessage);
        System.out.println(request.getAttribute("message"));
    }

    @RequestMapping("/null")
    @ResponseBody
    public void other(HttpServletRequest request) {

    }

//    @RequestMapping("/sendGroupMessage")
//    @ResponseBody
//    public String send_group_message(String message, int group_id) {
//        gocqUtils.send_group_message(group_id, message);
//        return "ok";
//    }
}
